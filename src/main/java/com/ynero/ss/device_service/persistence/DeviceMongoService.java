package com.ynero.ss.device_service.persistence;

import com.ynero.ss.device_service.domain.Device;
import com.ynero.ss.device_service.domain.Port;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

@Primary
public class DeviceMongoService implements DeviceService{

    @Autowired
    private DeviceRepository deviceRepository;

    @SneakyThrows
    @Override
    public Device save(Device device) {
        if (device.getId()!=null && device.getTenantId()!=null)
        deviceRepository.save(device);
        throw new Exception("Not enough data");
    }

    @Override
    public void updateSnapshot(Port port) {

    }

    @Override
    public Device[] getAllRelatedDevicesByEvent(Port port) {
        return new Device[0];
    }
}
