package com.ynero.ss.device_service.persistence;

import com.ynero.ss.device_service.domain.Device;
import com.ynero.ss.device_service.domain.Port;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface DeviceService {
    Device save(Device device);
    void updateSnapshot(Port port, UUID deviceId);
    Device[] getAllRelatedDevicesByPipelineIds(UUID pipelineId);
    Device getDeviceById(UUID id);
    Port addPort(Port port, UUID deviceId);
}
