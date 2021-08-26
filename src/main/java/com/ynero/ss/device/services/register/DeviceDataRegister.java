package com.ynero.ss.device.services.register;

import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.domain.Port;
import com.ynero.ss.device.persistence.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DeviceDataRegister {

    @Autowired
    private DeviceService deviceService;

    public Port register(Device device){
        var deviceId = device.getId();
        var foundDevice = deviceService.getDeviceById(deviceId);

        if (foundDevice == null){
            device = deviceService.save(device);
            foundDevice = device;
        }

        var currentPort = Arrays.stream(device.getPorts())
                .filter(
                        port -> port != null
                )
                .findFirst().orElseThrow();
        String nameOfCurrentPort = currentPort.getName();

        var foundPort = Arrays.stream(foundDevice.getPorts())
                .filter(
                        port -> port.getName().equals(nameOfCurrentPort)
                )
                .findFirst().get();
        if (foundPort == null) {
            foundPort = deviceService.addPort(currentPort, deviceId);
        }

        return foundPort;
    }
}
