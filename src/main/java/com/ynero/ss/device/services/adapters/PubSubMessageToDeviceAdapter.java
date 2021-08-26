package com.ynero.ss.device.services.adapters;

import com.google.pubsub.v1.PubsubMessage;
import com.ynero.ss.device.domain.Device;
import com.ynero.ss.device.domain.IncomingDeviceData;
import com.ynero.ss.device.domain.Port;
import com.ynero.ss.device.persistence.service.DeviceService;
import dtos.DeviceDTO;
import json_converters.DTOToMessageJSONConverter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class PubSubMessageToDeviceAdapter {

    private final DTOToMessageJSONConverter<DeviceDTO> converter;
    private final DeviceService deviceService;

    public PubSubMessageToDeviceAdapter(DTOToMessageJSONConverter<DeviceDTO> converter, DeviceService deviceService) {
        this.converter = converter;
        this.deviceService = deviceService;
    }
    @Setter(onMethod_ = {@Value("${tenant-id.attribute-key}")})
    private String tenantIdAttributeKey;

    // TODO:
    public IncomingDeviceData adapt(PubsubMessage deviceEventPubsubMessage) {

        var data = deviceEventPubsubMessage.getData().toStringUtf8();
        var deviceDTO = converter.deserialize(data,DeviceDTO.class);
        var messagePort = deviceDTO.getPort();
        var messageEvent = messagePort.getEvent();

        var portName = messagePort.getName();
        var valueForPort = messageEvent.getValue();
        var messageTimeArriving = messageEvent.getTime();
        var tenantId = deviceEventPubsubMessage.getAttributesMap().get(tenantIdAttributeKey);
        var deviceId = deviceDTO.getId();

        var port = Port.builder()
                .name(portName)
                .value(valueForPort)
                .lastUpdate(messageTimeArriving)
                .build();

        var ports = new ArrayList() {{add(port);}};

        var device = Device.builder()
                .id(deviceId)
                .ports(ports)
                .tenantId(tenantId)
                .build();

        var incomingDeviceData = new IncomingDeviceData(device, port);

        return incomingDeviceData;
    }
}
