package com.ynero.ss.device.services.categorizer;

import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.persistence.DeviceService;
import com.ynero.ss.device.services.register.DeviceDataRegister;
import com.ynero.ss.device.services.sender.PipelinesgRPCSender;
import com.ynero.ss.pipeline.dto.proto.PipelinesMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
public class DeviceDataCategorizer {
    private final DeviceService deviceService;
    private final DeviceDataRegister deviceDataRegister;
    private final PipelinesgRPCSender pipelinesgRPCSender;

    @Autowired
    public DeviceDataCategorizer(DeviceService deviceService, DeviceDataRegister deviceDataRegister,
            PipelinesgRPCSender pipelinesgRPCSender) {
        this.deviceService = deviceService;
        this.deviceDataRegister = deviceDataRegister;
        this.pipelinesgRPCSender = pipelinesgRPCSender;
    }

    public void categorize(Device device) {
        var port = deviceDataRegister.register(device);
        deviceService.updateSnapshot(port, device.getId());
        var pipelinesId = port.getPipelinesId();
        if (pipelinesId == null) {
            return;
        }
        var query = PipelinesMessage.PipelineQuery.newBuilder();

        for (UUID pipelineId : pipelinesId) {
            var devicesOfPipeline = deviceService.getAllRelatedDevicesByPipelineId(pipelineId);
            var pipelineDevicesMsg = PipelinesMessage.PipelineDevices.newBuilder();
            pipelineDevicesMsg.setPipelineId(pipelineId.toString());
            for (Device deviceOfPipeline : devicesOfPipeline) {
                var valueOfSearchingPortForCurrentDevice = deviceService.getSnapshot(port.getName(), deviceOfPipeline.getId())
                        .getValue();
                var deviceData = PipelinesMessage.DeviceData.newBuilder()
                        .setDeviceId(deviceOfPipeline.getId().toString())
                        .setPort(port.getName())
                        .setValue(valueOfSearchingPortForCurrentDevice.toString())
                        .build();
                pipelineDevicesMsg.addDevicesData(deviceData);
            }
            query.addPipelineDevices(pipelineDevicesMsg.build());
        }
        pipelinesgRPCSender.send(query.build());
    }
}
