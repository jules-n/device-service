package com.ynero.ss.device.persistence;

import com.ynero.ss.device.domain.Port;
import com.ynero.ss.device.domain.Device;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface DeviceService {
    Device save(Device device);
    void updateSnapshot(Port port, UUID deviceId);
    Device[] getAllRelatedDevicesByPipelineId(UUID pipelineId);
    Device getDeviceById(UUID id);
    Port addPort(Port port, UUID deviceId);
    Port getSnapshot(String portName, UUID deviceId);
    boolean addPipelineToPort(UUID pipelineId, String portName, UUID deviceId);
}
