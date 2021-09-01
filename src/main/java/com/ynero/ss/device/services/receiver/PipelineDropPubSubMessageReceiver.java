package com.ynero.ss.device.services.receiver;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.PubsubMessage;
import com.ynero.ss.device.persistence.service.DeviceService;
import dtos.PipelineDevicesDTO;
import dtos.PipelineIdDTO;
import json_converters.DTOToMessageJSONConverter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
public class PipelineDropPubSubMessageReceiver implements MessageReceiver {

    @Setter(onMethod_ = {@Autowired})
    private DeviceService deviceService;

    @Setter(onMethod_ = {@Autowired})
    private DTOToMessageJSONConverter<PipelineIdDTO> converter;

    @Override
    public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
        var data = message.getData().toStringUtf8();
        PipelineIdDTO dto = converter.deserialize(data, PipelineIdDTO.class);
        deviceService.removePipeline(dto.getPipelineId());
        log.info(message.getMessageId());
        consumer.ack();
    }
}
