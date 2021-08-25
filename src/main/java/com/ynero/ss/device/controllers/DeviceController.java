package com.ynero.ss.device.controllers;

import com.ynero.ss.device.persistence.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("pipelines")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PutMapping
    private ResponseEntity addPipelineToPort(@RequestParam String deviceId,
                                             @RequestParam String portName,
                                             @RequestParam String pipelineId){
        var UUIDPipelineId = UUID.fromString(pipelineId);
        var UUIDDeviceId = UUID.fromString(deviceId);
        deviceService.addPipelineToPort(UUIDPipelineId, portName, UUIDDeviceId);
        return ResponseEntity.ok().build();
    }
}
