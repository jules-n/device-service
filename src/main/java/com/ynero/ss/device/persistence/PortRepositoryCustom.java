package com.ynero.ss.device.persistence;

import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.domain.Port;

import java.util.List;
import java.util.UUID;

public interface PortRepositoryCustom {
    boolean updateSnapshot(Port port, UUID deviceId);
    Port findSnapshot(String portName, UUID deviceId);
    List<Device> getAllRelatedDevicesByPipelinesId(UUID pipelineId);
    Port addPort(Port port, UUID deviceId);
    boolean addPipelineToPort(UUID pipelineId, String portName, UUID deviceId);
}
