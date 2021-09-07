package com.ynero.ss.device.persistence.repository;

import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.domain.Port;

import java.util.List;
import java.util.UUID;

public interface DeviceRepositoryCustom {
    boolean updatePortValue(Port port, UUID deviceId);
    Port findPort(String portName, UUID deviceId);
    List<Device> getAllRelatedDevicesByPipelinesId(UUID pipelineId);
    Port addPort(Port port, UUID deviceId);
    boolean addPipelineToPort(UUID pipelineId, String portName, UUID deviceId);
    boolean removePipeline(UUID pipelineId);
    boolean updateDeviceData(Device device);
}
