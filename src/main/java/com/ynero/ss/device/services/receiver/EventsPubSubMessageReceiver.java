package com.ynero.ss.device.services.receiver;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.PubsubMessage;
import com.ynero.ss.device.services.adapters.PubSubMessageToDeviceAdapter;
import com.ynero.ss.device.services.categorizer.DeviceDataCategorizer;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Log4j2
public class EventsPubSubMessageReceiver implements MessageReceiver {

    @Setter(onMethod_ = {@Autowired})
    private DeviceDataCategorizer deviceDataCategorizer;

    @Setter(onMethod_ = {@Autowired})
    private PubSubMessageToDeviceAdapter pubSubMessageToDeviceAdapter;

    @Override
    public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
        var incomingDeviceData = pubSubMessageToDeviceAdapter.adapt(message);
        System.out.println(incomingDeviceData);
        log.info(incomingDeviceData);
        deviceDataCategorizer.categorize(incomingDeviceData.getDevice(), incomingDeviceData.getActivePort());
        consumer.ack();
    }
}
