package com.ynero.ss.device.persistence;

import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.domain.Port;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Primary
@Component
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private PortRepository portRepository;

    @SneakyThrows
    @Override
    public Device save(Device device) {
        if (device.getId() != null && device.getTenantId() != null) {
            return deviceRepository.save(device);
        }
        throw new Exception("Not enough data");
    }

    @Override
    public void updateSnapshot(Port port, UUID deviceId) {
        if(port.getLastUpdate()==null){
            port.setLastUpdate(LocalDateTime.now());
        }
        if (port.getName() != null && deviceId != null){
            portRepository.updateSnapshot(port, deviceId);
        }
    }

    @Override
    public List<Device> getAllRelatedDevicesByPipelineId(UUID pipelineId) {
        if (pipelineId != null)
            return portRepository.getAllRelatedDevicesByPipelinesId(pipelineId);
        throw new IllegalArgumentException();
    }

    @Override
    public Device getDeviceById(UUID id) {
        var device = deviceRepository.findById(id).orElse(null);
        return device;
    }

    @Override
    public Port addPort(Port port, UUID deviceId) {
        if(port.getLastUpdate()==null){
            port.setLastUpdate(LocalDateTime.now());
        }
        if (port.getName() != null && deviceId != null){
            return portRepository.addPort(port, deviceId);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Port getSnapshot(String portName, UUID deviceId) {
        if (portName != null && deviceId != null)
            return portRepository.findSnapshot(portName, deviceId);
        throw new IllegalArgumentException();
    }

    @SneakyThrows
    @Override
    public boolean addPipelineToPort(UUID pipelineId, String portName, UUID deviceId) {
        if(pipelineId!=null && portName!=null){
            if (deviceRepository.findById(deviceId)!=null) {
                return portRepository.addPipelineToPort(pipelineId, portName, deviceId);
            }
        }
        throw new Exception("Pipeline cant be added");
    }
}
