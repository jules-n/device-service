package com.ynero.ss.device.services.categorizer;

import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.domain.Port;
import com.ynero.ss.device.persistence.service.DeviceService;
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
    private final PipelinesgRPCSender pipelinesgRPCSender;

    @Autowired
    public DeviceDataCategorizer(DeviceService deviceService, PipelinesgRPCSender pipelinesgRPCSender) {
        this.deviceService = deviceService;
        this.pipelinesgRPCSender = pipelinesgRPCSender;
    }

    public void categorize(Device device, Port activePort) {
        var port = deviceService.findOrSave(device, activePort);
        var pipelinesId = port.getPipelinesId();
        if (pipelinesId == null || pipelinesId.size() == 0) {
            return;
        }
        var pipelineExecutionReq = PipelinesMessage.PipelineQuery.newBuilder();

        for (UUID pipelineId : pipelinesId) {
            var devicesOfPipeline = deviceService.getAllRelatedDevicesByPipelineId(pipelineId);
            var pipelineDevicesMsg = PipelinesMessage.PipelineDevices.newBuilder();
            pipelineDevicesMsg.setPipelineId(pipelineId.toString());
            for (Device deviceOfPipeline : devicesOfPipeline) {
                var valueOfSearchingPortForCurrentDevice = deviceService.getPort(port.getName(), deviceOfPipeline.getId())
                        .getValue();
                var deviceData = PipelinesMessage.DeviceData.newBuilder()
                        .setDeviceId(deviceOfPipeline.getId().toString())
                        .setPort(port.getName())
                        .setValue(valueOfSearchingPortForCurrentDevice.toString())
                        .build();
                pipelineDevicesMsg.addDevicesData(deviceData);
            }
            pipelineExecutionReq.addPipelineDevices(pipelineDevicesMsg.build());
        }
        pipelinesgRPCSender.send(pipelineExecutionReq.build());
    }
}
