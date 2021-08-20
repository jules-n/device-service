package com.ynero.ss.device_service.persistence;

import com.ynero.ss.device_service.domain.Device;
import com.ynero.ss.device_service.domain.Port;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

import java.util.UUID;

@Primary
public class DeviceServiceMongoImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private PortRepository portRepository;

    @SneakyThrows
    @Override
    public Device save(Device device) {
        if (device.getId() != null && device.getTenantId() != null)
            deviceRepository.save(device);
        throw new Exception("Not enough data");
    }

    @Override
    public void updateSnapshot(Port port, UUID deviceId) {
        if (port.getName() != null && deviceId != null)
            portRepository.updateSnapshot(port, deviceId);
    }

    @Override
    public Device[] getAllRelatedDevicesByPipelineId(UUID pipelineId) {
        if(pipelineId!=null)
        return portRepository.getAllRelatedDevicesByPipelinesId(pipelineId);
        throw new IllegalArgumentException();
    }

    @Override
    public Device getDeviceById(UUID id) {
        return deviceRepository.findById(id).orElseThrow();
    }

    @Override
    public Port addPort(Port port, UUID deviceId) {
        if (port.getName() != null && deviceId != null)
            return portRepository.addPort(port, deviceId);
        throw new IllegalArgumentException();
    }
}
