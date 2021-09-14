
package com.ynero.ss.device.services.sender.devicesender;

import com.ynero.ss.device.domain.Port;
import domain.PortSendingDataDTO;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PortSendingDataSender {

    @Setter(onMethod_ = {@Autowired})
    private KafkaSender<PortSendingDataDTO> sender;



    @Value("${spring.kafka.producer.port-sending-data-topic}")
    private String topic;


    public void send(Port port, UUID deviceId, String tenantId) {
        var sendingParameters = port.getSendingParameters();
        if (sendingParameters == null) return;
        var portSendingData = PortSendingDataDTO.builder()
                .portName(port.getName())
                .deviceId(deviceId)
                .tenantId(tenantId)
                .parameters(sendingParameters);
        sender.produce(portSendingData.build(), topic);
    }
}

