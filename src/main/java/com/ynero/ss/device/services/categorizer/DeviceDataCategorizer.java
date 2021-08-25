package com.ynero.ss.device.services.categorizer;

import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.persistence.DeviceService;
import com.ynero.ss.device.services.register.DeviceDataRegister;
//import com.ynero.ss.device.services.sender.PipelinesgRPCSender;
/*import com.ynero.ss.pipeline.dto.proto.PipelinesMessage;*/
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
public class DeviceDataCategorizer {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceDataRegister deviceDataRegister;

    /*@Autowired
    private PipelinesgRPCSender pipelinesgRPCSender;*/

    public void categorize(Device device) {

       /* var port = deviceDataRegister.register(device);
        deviceService.updateSnapshot(port, device.getId());
        var pipelinesId = port.getPipelinesId();
        var query = PipelinesMessage.PipelineQuery.newBuilder();

        for (UUID id : pipelinesId) {
            Device[] devices = deviceService.getAllRelatedDevicesByPipelineId(id);
            var pipelineDevices = PipelinesMessage.PipelineDevices.newBuilder();
            pipelineDevices.setPipelineId(id.toString());
            for (Device _device : devices) {
                var valueOfSearchingPortForCurrentDevice = deviceService.getSnapshot(port,_device.getId()).getValue();
                var deviceData = PipelinesMessage.DeviceData.newBuilder()
                        .setDeviceId(_device.getId().toString())
                        .setPort(port.getName())
                        .setValue(valueOfSearchingPortForCurrentDevice.toString())
                        .build();
                pipelineDevices.addDevicesData(deviceData);
            }
            query.addPipelineDevices(pipelineDevices.build());
        }
        pipelinesgRPCSender.send(query.build());*/
    }
}
