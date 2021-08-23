package com.ynero.ss.device_service.persistence;

import com.ynero.ss.device_service.domain.Device;
import com.ynero.ss.device_service.domain.Port;

import java.util.UUID;

public interface PortRepositoryCustom {
    boolean updateSnapshot(Port port, UUID deviceId);
    Port findSnapshot(Port port, UUID deviceId);
    Device[] getAllRelatedDevicesByPipelinesId(UUID pipelineId);
    Port addPort(Port port, UUID deviceId);
}
