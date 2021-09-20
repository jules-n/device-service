package com.ynero.ss.device.services.categorizer;

import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.domain.Port;
import com.ynero.ss.device.persistence.service.DeviceService;
import com.ynero.ss.device.services.sender.analytics.DataFromDeviceSender;
import com.ynero.ss.device.services.sender.execution.PipelinesgRPCSender;
import com.ynero.ss.pipeline.dto.proto.PipelinesMessage;
import lombok.extern.log4j.Log4j2;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@Log4j2
class DeviceDataCategorizerTest {

    @Mock
    private DeviceService deviceService;

    @Mock
    private PipelinesgRPCSender pipelinesgRPCSender;

    @Mock
    private DataFromDeviceSender dataFromDeviceSender;

    @InjectMocks
    private DeviceDataCategorizer categorizer;

    @Captor
    ArgumentCaptor<PipelinesMessage.PipelineQuery> pipelineMsgCaptor;

    private UUID deviceId = UUID.randomUUID();
    private String tenantId = "test-tenant-id";
    private String activePortName = "test-active-port-name";
    private String activePortValue = "36.6";
    private Port activePortWithNoPipelines;
    private Device eventOnPortWithNoPipelines;
    private Port activePortWithPipelines;
    private Device eventOnPortWithPipelines;
    private UUID pipelineId_1 = UUID.randomUUID();
    private UUID pipelineId_2 = UUID.randomUUID();

    @BeforeEach
    void setup() {
        // no pipelines
        activePortWithNoPipelines = Port.builder()
                .name(activePortName)
                .lastUpdate(LocalDateTime.now())
                .value(activePortValue)
                .pipelinesId(new ArrayList<UUID>())
                .build();
        eventOnPortWithNoPipelines = Device.builder()
                .id(deviceId)
                .tenantId(tenantId)
                .ports(new ArrayList<Port>(){{add(activePortWithNoPipelines);}})
                .build();

        // with pipelines
        activePortWithPipelines = Port.builder()
                .name(activePortName)
                .lastUpdate(LocalDateTime.now())
                .value(activePortValue)
                .pipelinesId(List.of(pipelineId_1, pipelineId_2))
                .build();
        eventOnPortWithPipelines = Device.builder()
                .id(deviceId)
                .tenantId(tenantId)
                .ports(new ArrayList<Port>(){{add(activePortWithPipelines);}})
                .build();
    }

    @Test
    void categorize_DoesNotSendPipelineForExecution_WhenNoPipelinesAssociatedWithEventOnPortFound () {
        // given: what is test setup?
        // given: event on port, and there are NO any pipelines associated with this port
        when(deviceService.findOrSave(eq(eventOnPortWithNoPipelines), eq(activePortWithNoPipelines)))
                .thenReturn(activePortWithNoPipelines);
        // when: what action is performed?
        // when: categorize this event
        categorizer.categorize(eventOnPortWithNoPipelines, activePortWithNoPipelines);

        // then: NO outbound request for pipeline execution is sent
        verify(pipelinesgRPCSender, never()).send(any());
    }

    @Test
    @SuppressWarnings("Convert2MethodRef")
    void categorize_BuildsAndSendsPipelineExecutionRequest_WhenOnePipelineIsAssociatedWithPortPort() {
        // given: event on port, and there is ONE pipeline associated with this port
        when(deviceService.findOrSave(eq(eventOnPortWithPipelines), eq(activePortWithPipelines)))
                .thenReturn(activePortWithPipelines);
        when(deviceService.getPort(eq(activePortWithPipelines.getName()),eq(eventOnPortWithPipelines.getId())))
                .thenReturn(activePortWithPipelines);
        when(deviceService.getAllRelatedDevicesByPipelineId(eq(activePortWithPipelines.getPipelinesId().get(0))))
                .thenReturn(Collections.singletonList(eventOnPortWithPipelines));
        when(deviceService.getAllRelatedDevicesByPipelineId(eq(activePortWithPipelines.getPipelinesId().get(1))))
                .thenReturn(Collections.singletonList(eventOnPortWithPipelines));

        // when: categorize this event
        categorizer.categorize(eventOnPortWithPipelines, activePortWithPipelines);

        // then: pipeline execution request for single pipeline is sent out
        verify(pipelinesgRPCSender, times(1)).send(pipelineMsgCaptor.capture());

        // and: pipeline msg has two pipelines
        var actualOutgoingPipelineMsg = pipelineMsgCaptor.getValue();
        assertThat(actualOutgoingPipelineMsg).isNotNull();
        assertThat(actualOutgoingPipelineMsg.getPipelineDevicesCount())
                .isEqualTo(2);

        // and: correct tenantId is set
        // TODO: update this test when DTO model is updated to include tenantId
        assertThat(actualOutgoingPipelineMsg.getTenantId()).isEqualTo(tenantId);

        // and: two pipelines are included
        assertThat(actualOutgoingPipelineMsg.getPipelineDevicesList())
                .map(PipelinesMessage.PipelineDevices::getPipelineId)
                .containsExactlyInAnyOrder(pipelineId_1.toString(), pipelineId_2.toString());

        // and: correct port name is set
        assertThat(actualOutgoingPipelineMsg.getPipelineDevicesList())
                .flatMap(devicesData -> devicesData.getDevicesDataList())
                .map(data -> data.getPort())
                .containsOnly(activePortName);

        // and: port value is set correctly
        assertThat(actualOutgoingPipelineMsg.getPipelineDevicesList())
                .flatMap(devices -> devices.getDevicesDataList())
                .map(data -> data.getValue())
                .containsOnly(activePortValue);
    }
}
