package com.ynero.ss.device_service.persistence;

import com.ynero.ss.device_service.domain.Device;
import com.ynero.ss.device_service.domain.Port;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

@Primary
public class DeviceMongoService implements DeviceService{

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public Device save(Device device) {
        return null;
    }

    @Override
    public void updateSnapshot(Device device) {

    }

    @Override
    public Device[] getAllRelatedDevicesByEvent(Port port) {
        return new Device[0];
    }
}
