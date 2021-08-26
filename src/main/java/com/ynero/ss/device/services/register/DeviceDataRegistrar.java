package com.ynero.ss.device.services.register;

import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.domain.Port;
import com.ynero.ss.device.persistence.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

// TODO: refactor: move single method of this service to DeviceService
@Service
public class DeviceDataRegistrar {

    @Autowired
    private DeviceService deviceService;

    // TODO: refactor: rename this method to more obvious name, and provide port in arguments
    public Port register(Device device){
        var deviceId = device.getId();
        var existingDevice = deviceService.getDeviceById(deviceId);

        if (existingDevice == null){
            device = deviceService.save(device);
            existingDevice = device;
        }

        var activePort = Arrays.stream(device.getPorts())
                .filter(port -> port != null)
                .findFirst().orElseThrow();
        String nameOfCurrentPort = activePort.getName();

        var existingPort = Arrays.stream(existingDevice.getPorts())
                .filter(
                        port -> port.getName().equals(nameOfCurrentPort)
                )
                .findFirst().get();
        if (existingPort == null) {
            existingPort = deviceService.addPort(activePort, deviceId);
        }

        return existingPort;
    }
}
