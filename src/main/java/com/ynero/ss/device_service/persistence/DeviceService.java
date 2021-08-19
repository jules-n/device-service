package com.ynero.ss.device_service.persistence;

import com.ynero.ss.device_service.domain.Device;
import com.ynero.ss.device_service.domain.Port;
import org.springframework.stereotype.Service;

@Service
public interface DeviceService {
    Device save(Device device);
    void updateSnapshot(Device device);
    Device[] getAllRelatedDevicesByEvent(Port port);

}
