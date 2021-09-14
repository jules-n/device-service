package com.ynero.ss.device.services.sender.devicesender;

import com.ynero.ss.device.domain.Device;
import domain.DeviceSendingDataDTO;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DeviceSendingDataSender {

    @Setter(onMethod_ = {@Autowired})
    private KafkaSender<DeviceSendingDataDTO> sender;

    @Setter(onMethod_ = {@Value("${spring.kafka.producer.device-sending-data-topic}")})
    private String topic;

    public void send(Device device) {
        var sendingParameters = device.getSendingParameters();
        if (sendingParameters == null) return;
        var deviceSendingData = DeviceSendingDataDTO.builder()
                .deviceId(device.getId())
                .tenantId(device.getTenantId())
                .parameters(sendingParameters);
        sender.produce(deviceSendingData.build(), topic);
    }
}

