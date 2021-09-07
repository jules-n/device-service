package com.ynero.ss.device.controller;

import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.domain.dto.DeviceDTO;
import com.ynero.ss.device.persistence.service.DeviceService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("devices")
public class DeviceController {

    @Setter(onMethod_ = {@Autowired})
    private ModelMapper modelMapper;

    @Setter(onMethod_ = {@Autowired})
    private DeviceService deviceService;

    @PostMapping
    private ResponseEntity save(@RequestBody DeviceDTO dto) {
        var device = modelMapper.map(dto, Device.class);
        var result = deviceService.save(device);
        return result == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
    }

    @PutMapping
    private ResponseEntity update(@RequestBody DeviceDTO dto) {
        var device = modelMapper.map(dto, Device.class);
        var result = deviceService.updateDeviceData(device);
        return result ? ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
    }
}
