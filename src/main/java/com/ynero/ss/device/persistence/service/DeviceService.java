package com.ynero.ss.device.persistence.service;

import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.domain.Port;

import java.util.List;
import java.util.UUID;

public interface DeviceService {
    Port findOrSave(Device device, Port activePort);
    Device save(Device device);
    void updatePortValue(Port port, UUID deviceId);
    List<Device> getAllRelatedDevicesByPipelineId(UUID pipelineId);
    Device getDeviceById(UUID id);
    Port addPort(Port port, UUID deviceId);
    Port getPort(String portName, UUID deviceId);
    boolean addPipelineToPort(UUID pipelineId, String portName, UUID deviceId);
}
