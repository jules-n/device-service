package com.ynero.ss.device.persistence.service;

import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.domain.Port;
import com.ynero.ss.device.persistence.repository.DeviceRepository;
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

    @SneakyThrows
    @Override
    public Device save(Device device) {
        if (device.getId() != null && device.getTenantId() != null) {
            return deviceRepository.save(device);
        }
        throw new Exception("Not enough data");
    }

    @SneakyThrows
    @Override
    public Port findOrSave(Device device, Port activePort) {
        var deviceId = device.getId();
        var existingDevice = getDeviceById(deviceId);

        if (existingDevice == null){
            existingDevice = save(device);
        }

        String nameOfCurrentPort = activePort.getName();

        var existingPort = existingDevice.getPorts().stream()
                .filter(
                        port -> port.getName().equals(nameOfCurrentPort)
                )
                .findFirst().get();

        if (existingPort == null) {
            existingPort = addPort(activePort, deviceId);
        }
        else {
            updatePortValue(activePort, deviceId);
            existingPort.setLastUpdate(activePort.getLastUpdate());
            existingPort.setValue(activePort.getValue());
        }
        return existingPort;
    }

    @Override
    public void updatePortValue(Port port, UUID deviceId) {
        if(port.getLastUpdate()==null){
            port.setLastUpdate(LocalDateTime.now());
        }
        if (port.getName() != null && deviceId != null){
            deviceRepository.updatePortValue(port, deviceId);
        }
    }

    @Override
    public List<Device> getAllRelatedDevicesByPipelineId(UUID pipelineId) {
        if (pipelineId != null)
            return deviceRepository.getAllRelatedDevicesByPipelinesId(pipelineId);
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
            return deviceRepository.addPort(port, deviceId);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Port getPort(String portName, UUID deviceId) {
        if (portName != null && deviceId != null)
            return deviceRepository.findPort(portName, deviceId);
        throw new IllegalArgumentException();
    }

    @SneakyThrows
    @Override
    public boolean addPipelineToPort(UUID pipelineId, String portName, UUID deviceId) {
        if(pipelineId!=null && portName!=null){
            if (deviceRepository.findById(deviceId)!=null) {
                return deviceRepository.addPipelineToPort(pipelineId, portName, deviceId);
            }
        }
        throw new Exception("Pipeline cant be added");
    }

    @Override
    public boolean removePipeline(UUID pipelineId) {
        if(pipelineId!=null){
            return deviceRepository.removePipeline(pipelineId);
        }
        return false;
    }
}
