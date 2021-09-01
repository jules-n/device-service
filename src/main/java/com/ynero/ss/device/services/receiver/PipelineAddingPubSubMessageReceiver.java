package com.ynero.ss.device.services.receiver;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.PubsubMessage;
import com.ynero.ss.device.persistence.service.DeviceService;
import dtos.PipelineDevicesDTO;
import json_converters.DTOToMessageJSONConverter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
public class PipelineAddingPubSubMessageReceiver implements MessageReceiver {

    @Setter(onMethod_ = {@Autowired})
    private DeviceService deviceService;

    @Setter(onMethod_ = {@Autowired})
    private DTOToMessageJSONConverter<PipelineDevicesDTO> converter;

    @Override
    public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
        var data = message.getData().toStringUtf8();
        PipelineDevicesDTO dto = converter.deserialize(data, PipelineDevicesDTO.class);
        dto.getDeviceIds().forEach(
                id -> {
                    deviceService.addPipelineToPort(UUID.fromString(dto.getPipelineId()), dto.getPortName(), UUID.fromString(id));
                }
        );
        log.info(message.getMessageId());
        consumer.ack();
    }
}
