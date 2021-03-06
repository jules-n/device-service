package com.ynero.ss.device.persistence;

import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.domain.Port;
import com.ynero.ss.device.persistence.repository.DeviceRepository;
import com.ynero.ss.device.persistence.service.DeviceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureDataMongo
@ActiveProfiles("integration-test")
@Testcontainers
@DirtiesContext
public class DeviceServiceTest {

    public static final String MONGO_VERSION = "4.4.4";

    @Autowired
    protected MongoOperations mongo;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceRepository deviceRepository;

    @Container
    protected static final MongoDBContainer MONGO_CONTAINER = new MongoDBContainer("mongo:" + MONGO_VERSION);

    @DynamicPropertySource
    protected static void mongoProperties(DynamicPropertyRegistry reg) {
        reg.add("spring.data.mongodb.uri", () -> {
            return MONGO_CONTAINER.getReplicaSetUrl();
        });
    }

    @AfterEach
    protected void cleanupAllDataInDb() {
        mongo.getCollectionNames().forEach(collection -> {
            mongo.remove(new Query(), collection);
        });
    }

    private List<Device> devices;
    private UUID testedPipelineId;

    @BeforeEach
    void setUp() {
        testedPipelineId = UUID.fromString("6698d204-af19-4524-b3c6-53c907aa8b58");
        devices = new ArrayList<>() {{
            add(new Device(UUID.randomUUID(), "SubWay",
                    new ArrayList<Port>() {{
                        add(new Port("temperature", 25,
                                LocalDateTime.of(2021, 8, 22, 22, 59, 12),
                                new ArrayList<UUID>() {{
                                    add(UUID.randomUUID());
                                    add(testedPipelineId);
                                    add(UUID.randomUUID());
                                }}, null));
                        add(new Port("humidity", 65,
                                LocalDateTime.of(2021, 8, 6, 14, 20, 13),
                                new ArrayList<UUID>() {{
                                    add(UUID.randomUUID());
                                }}, null));
                        add(new Port("err", "Bad connection",
                                LocalDateTime.of(2021, 2, 13, 22, 2, 17),
                                null, null));
                    }}, null));
            add(new Device(UUID.randomUUID(), "MARVEL",
                    new ArrayList<Port>() {{
                        add(new Port("battery", 10,
                                LocalDateTime.of(2021, 8, 23, 17, 40, 22),
                                null, null));
                    }}, null));
            add(new Device(UUID.randomUUID(), "Subway",
                    new ArrayList<Port>() {{
                        add(new Port("temperature", 39,
                                LocalDateTime.of(2021, 8, 21, 13, 12, 15),
                                new ArrayList<UUID>() {{
                                    add(testedPipelineId);
                                    add(UUID.randomUUID());
                                }}, null));
                        add(new Port("humidity", 91,
                                LocalDateTime.of(2021, 8, 16, 15, 22, 15),
                                new ArrayList<UUID>() {{
                                    add(UUID.randomUUID());
                                }}, null));
                    }}, null));
            add(new Device(UUID.randomUUID(), "SubWay",
                    new ArrayList<Port>() {{
                        add(new Port("humidity", 77,
                                LocalDateTime.of(2021, 8, 23, 14, 20, 13),
                                new ArrayList<UUID>() {{
                                    add(UUID.randomUUID());
                                }}, null));
                    }}, null));
        }};

        for (Device device : devices) {
            deviceService.save(device);
        }
    }

    @Test
    void getDeviceByIdShouldReturnCorrectDeviceAccordingId() {
        var actual = deviceService.getDeviceById(devices.get(1).getId());
        var expected = devices.get(1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void assertThatNewPortAdded() {
        var idOfFirstDevice = devices.get(0).getId();
        var newPort = new Port("gps", new Point(11.0f, 15.0f), LocalDateTime.now(), null, null);
        deviceService.addPort(newPort, idOfFirstDevice);
        var expectedListOfPortsName = deviceService
                .getDeviceById(idOfFirstDevice)
                .getPorts().stream().
                        map(port -> port.getName());
        assertThat(expectedListOfPortsName).contains(newPort.getName());

    }

    @Test
    void getSnapshotShouldReturnCorrectDataForDevice() {
        var idOfThirdDevice = devices.get(3).getId();
        var nameOfFirstPortOfThirdDevice = devices.get(3).getPorts().get(0).getName();
        var expectedValueOfFirstPortOfThirdDevice = devices.get(3).getPorts().get(0).getValue();
        var actualValueOfFirstPortOfThirdDevice = deviceService.getPort(nameOfFirstPortOfThirdDevice, idOfThirdDevice).getValue();
        assertThat(actualValueOfFirstPortOfThirdDevice).isEqualTo(expectedValueOfFirstPortOfThirdDevice);
    }

    @Test
    void getAllRelatedDevicesByPipelineIdShouldReturnAllDevicesWithSpecifiedPipeline() {
        var actualDevicesWithTestedPipelineId = deviceService.getAllRelatedDevicesByPipelineId(testedPipelineId);
        var expectedDevicesWithTestedPipelineId = new Device[]{devices.get(0), devices.get(2)};
        assertThat(actualDevicesWithTestedPipelineId).containsExactlyInAnyOrder(expectedDevicesWithTestedPipelineId);
    }

    @Test
    void updateSnapshotShouldChangeValueOnSpecifiedPort() {
        var expectedNewValue = 58;
        var updatedDevice = devices.get(3);
        var updatedPort = updatedDevice.getPorts().get(0);
        var newTime = LocalDateTime.now();
        var newPortsData = new Port(updatedPort.getName(), expectedNewValue,
                LocalDateTime.of(newTime.getYear(), newTime.getMonth(), newTime.getDayOfMonth(), newTime.getHour(), newTime.getMinute(), newTime.getSecond()),
                updatedPort.getPipelinesId(), null);
        deviceService.updatePortValue(newPortsData, updatedDevice.getId());
        var actualPort = deviceService.getDeviceById(updatedDevice.getId()).getPorts().get(0);
        assertThat(actualPort).isEqualTo(newPortsData);

    }

    @Test
    void addPipelineShouldUpdateArrayOfPipelinesIdOnSpecifiedPort() {
        var newPipelineId = UUID.randomUUID();
        var updatedDevice = devices.get(3);
        var updatedPort = updatedDevice.getPorts().get(0);
        var expectedPipelinesIdArray = updatedPort.getPipelinesId();
        expectedPipelinesIdArray.add(newPipelineId);
        deviceService.addPipelineToPort(newPipelineId, updatedPort.getName(), updatedDevice.getId());
        var actualPipelinesIdArray = deviceService.getDeviceById(updatedDevice.getId()).getPorts().get(0).getPipelinesId();
        assertThat(actualPipelinesIdArray).isEqualTo(expectedPipelinesIdArray);
    }

    @Test
    void removePipelineShouldUpdateAllDevicesWithSpecifiedPipeline() {
        deviceService.removePipeline(testedPipelineId);
        var actualDevices = deviceRepository.findAll();
        actualDevices.forEach(
                device -> {
                    device.getPorts().forEach(
                            port -> {
                                if(port.getPipelinesId()!=null)
                                assertThat(port.getPipelinesId()).doesNotContain(testedPipelineId);
                            }
                    );
                }
        );
    }
}
