package com.ynero.ss.device.services.categorizer;

import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.persistence.DeviceService;
import com.ynero.ss.device.services.register.DeviceDataRegistrar;
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
    private final DeviceDataRegistrar deviceDataRegistrar;
    private final PipelinesgRPCSender pipelinesgRPCSender;

    @Autowired
    public DeviceDataCategorizer(DeviceService deviceService, DeviceDataRegistrar deviceDataRegistrar,
            PipelinesgRPCSender pipelinesgRPCSender) {
        this.deviceService = deviceService;
        this.deviceDataRegistrar = deviceDataRegistrar;
        this.pipelinesgRPCSender = pipelinesgRPCSender;
    }

    // TODO: more obvious signature here
//    public void categorize(Device device, Port activePort) {
    public void categorize(Device device) {
        var port = deviceDataRegistrar.register(device);
        deviceService.updateSnapshot(port, device.getId());
        var pipelinesId = port.getPipelinesId();
        if (pipelinesId == null || pipelinesId.length == 0) {
            return;
        }
        var pipelineExecutionReq = PipelinesMessage.PipelineQuery.newBuilder();

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
            pipelineExecutionReq.addPipelineDevices(pipelineDevicesMsg.build());
        }
        pipelinesgRPCSender.send(pipelineExecutionReq.build());
    }
}
