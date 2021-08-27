package com.ynero.ss.device.services.categorizer;

import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.domain.Port;
import com.ynero.ss.device.persistence.service.DeviceService;
import com.ynero.ss.device.services.sender.PipelinesgRPCSender;
import lombok.extern.log4j.Log4j2;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

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

    @InjectMocks
    private DeviceDataCategorizer categorizer;

    private UUID deviceId = UUID.randomUUID();
    private String tenantId = "test-tenant-id";
    private String activePortName = "test-active-port-name";
    private String eventValue = "36.6";
    private Port activePortWithNoPipelines;
    private Device eventOnPortWithNoPipelines;
    private Port activePortWithPipelines;
    private Device eventOnPortWithPipelines;

    @BeforeEach
    void setup() {
        activePortWithNoPipelines = Port.builder()
                .name(activePortName)
                .lastUpdate(LocalDateTime.now())
                .value(eventValue)
                .pipelinesId(new ArrayList<UUID>())
                .build();
        eventOnPortWithNoPipelines = Device.builder()
                .id(deviceId)
                .tenantId(tenantId)
                .ports(new ArrayList<Port>(){{add(activePortWithNoPipelines);}})
                .build();
        activePortWithPipelines = Port.builder()
                .name(activePortName)
                .lastUpdate(LocalDateTime.now())
                .value(eventValue)
                .pipelinesId(new ArrayList<UUID>(){{
                    add(UUID.randomUUID());
                    add(UUID.randomUUID());
                }})
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
    @Ignore
    void categorize_SavesValueOnPort_WhenEventOnPortReceived() {
        // given: event on port
        when(deviceService.findOrSave(eq(eventOnPortWithNoPipelines), eq(activePortWithNoPipelines)))
                .thenReturn(activePortWithNoPipelines);
        // when: categorize event
        categorizer.categorize(eventOnPortWithNoPipelines, activePortWithNoPipelines);

        // then: value on port is persisted
        verify(deviceService, times(1)).updatePortValue(eq(activePortWithNoPipelines), eq(deviceId));
    }

    @Test
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
        verify(pipelinesgRPCSender, times(1)).send(any());

        //and:
    }
}
