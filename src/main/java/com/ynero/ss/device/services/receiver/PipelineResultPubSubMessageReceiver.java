package com.ynero.ss.device.services.receiver;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.PubsubMessage;
import dtos.PipelineIdDTO;
import json_converters.DTOToMessageJSONConverter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class PipelineResultPubSubMessageReceiver implements MessageReceiver {

    @Setter(onMethod_ = {@Autowired})
    private DTOToMessageJSONConverter<PipelineIdDTO> converter;

    @Override
    public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
        var data = message.getData().toStringUtf8();
        List<Map<String, Object>> results = converter.deserialize(data, List.class);
        results.forEach(
                result -> { log.info(result);}
        );
        consumer.ack();
    }
}
