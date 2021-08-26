package com.ynero.ss.device.services.categorizer;

import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.domain.Port;
import com.ynero.ss.device.persistence.DeviceService;
import com.ynero.ss.device.services.register.DeviceDataRegistrar;
import com.ynero.ss.device.services.sender.PipelinesgRPCSender;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
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
    private DeviceDataRegistrar deviceDataRegistrar;

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

    @BeforeEach
    void setup() {
        activePortWithNoPipelines = Port.builder()
                .name(activePortName)
                .lastUpdate(LocalDateTime.now())
                .value(eventValue)
                .pipelinesId(new UUID[]{})
                .build();
        eventOnPortWithNoPipelines = Device.builder()
                .id(deviceId)
                .tenantId(tenantId)
                .ports(new Port[]{activePortWithNoPipelines})
                .build();
        when(deviceDataRegistrar.register(eq(eventOnPortWithNoPipelines))).thenReturn(activePortWithNoPipelines);
    }

    @Test
    void categorize_DoesNotSendPipelineForExecution_WhenNoPipelinesAssociatedWithEventOnPortFound () {
        // given: what is test setup?
        // given: event on port, and there are NO any pipelines associated with this port

        // when: what action is performed?
        // when: categorize this event
        categorizer.categorize(eventOnPortWithNoPipelines);

        // then: NO outbound request for pipeline execution is sent
        verify(pipelinesgRPCSender, never()).send(any());
    }

    @Test
    void categorize_SavesValueOnPort_WhenEventOnPortReceived() {
        // given: event on port

        // when: categorize event
        categorizer.categorize(eventOnPortWithNoPipelines);

        // then: value on port is persisted
        verify(deviceService, times(1)).updateSnapshot(eq(activePortWithNoPipelines), eq(deviceId));
    }

    @Test
    void categorize_BuildsAndSendsPipelineExecutionRequest_WhenOnePipelineIsAssociatedWithPortPort() {
        // given: event on port, and there is ONE pipeline associated with this port

        // when: categorize this event

        // then: pipeline execution request for single pipeline is sent out
    }
}
