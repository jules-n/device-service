package com.ynero.ss.device.services.adapters;

import com.google.pubsub.v1.PubsubMessage;
import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.domain.Port;
import com.ynero.ss.device.persistence.DeviceService;
import dtos.DeviceDTO;
import json_converters.DTOToMessageJSONConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PubSubMessageToDeviceAdapter {

    @Autowired
    private DTOToMessageJSONConverter<DeviceDTO> converter;

    @Autowired
    private DeviceService deviceService;

    public Device adapt(PubsubMessage pubsubMessage) {

        var data = pubsubMessage.getData().toStringUtf8();
        var deviceDTO = converter.deserialize(data,DeviceDTO.class);
        var messagePort = deviceDTO.getPort();
        var messageEvent = messagePort.getEvent();

        var portName = messagePort.getName();
        var valueForPort = messageEvent.getValue();
        var messageTimeArriving = messageEvent.getTime();
        var tenantId = pubsubMessage.getAttributesMap().get("tenant-id");
        var deviceId = deviceDTO.getId();

        var port = Port.builder()
                .name(portName)
                .value(valueForPort)
                .lastUpdate(messageTimeArriving)
                .build();

        Port[] ports = new Port[]{port};

        var device = Device.builder()
                .id(deviceId)
                .ports(ports)
                .tenantId(tenantId)
                .build();

        return device;
    }
}
