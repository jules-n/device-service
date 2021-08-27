package com.ynero.ss.device.services.sender.analytics;

import dtos.PortValueDTO;
import json_converters.DTOToMessageJSONConverter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataFromDeviceKafkaSender {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final DTOToMessageJSONConverter<PortValueDTO> deviceDTOToMessageJSONConverter;

    @Setter(onMethod_ = {@Value("${spring.kafka.producer.for-save-topic}")})
    private String topic;

    public DataFromDeviceKafkaSender(KafkaTemplate<String, String> kafkaTemplate, DTOToMessageJSONConverter<PortValueDTO> deviceDTOToMessageJSONConverter) {
        this.kafkaTemplate = kafkaTemplate;
        this.deviceDTOToMessageJSONConverter = deviceDTOToMessageJSONConverter;
    }

    public void produce(PortValueDTO portValue) {
        var message = deviceDTOToMessageJSONConverter.serialize(portValue);
        kafkaTemplate.send(topic, message);
        kafkaTemplate.flush();
        kafkaTemplate.destroy();
    }
}
