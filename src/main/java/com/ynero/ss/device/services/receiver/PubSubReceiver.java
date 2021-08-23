package com.ynero.ss.device_service.services.receiver;

import com.ynero.ss.device_service.services.adapters.PubSubMessageToDeviceAdapter;
import com.ynero.ss.device_service.services.categorizer.DeviceDataCategorizer;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PubSubReceiver {

    @Value("${spring.cloud.stream.bindings.input.destination}")
    private String subscription;

    @Value("${spring.cloud.gcp.project-id}")
    private String projectId;

    @Autowired
    private DeviceDataCategorizer deviceDataCategorizer;

    @Autowired
    private PubSubMessageToDeviceAdapter pubSubMessageToDeviceAdapter;


    @Bean
    public void asyncSubscribing() {
        ProjectSubscriptionName subscriptionName =
                ProjectSubscriptionName.of(projectId, subscription);

        MessageReceiver receiver =
                (PubsubMessage pubsubMessage, AckReplyConsumer consumer) -> {
                    var device = pubSubMessageToDeviceAdapter.adapt(pubsubMessage);
                    deviceDataCategorizer.categorize(device);
                    consumer.ack();
                };

        Subscriber subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
            subscriber.startAsync().awaitRunning();
            subscriber.awaitTerminated();
    }


}
