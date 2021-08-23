package com.ynero.ss.device.services.receiver;

import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.ynero.ss.device.services.categorizer.DeviceDataCategorizer;
import com.ynero.ss.device.services.adapters.PubSubMessageToDeviceAdapter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@ConditionalOnProperty(name = "enablingPubSub")
@Configuration
public class PubSubReceiver {

    @Setter(onMethod_={@Value("${spring.cloud.stream.bindings.input.destination}")})
    private String subscription;

    @Setter(onMethod_={@Value("${spring.cloud.gcp.project-id}")})
    private String projectId;

    @Setter(onMethod_={@Autowired})
    private DeviceDataCategorizer deviceDataCategorizer;

    @Setter(onMethod_={@Autowired})
    private PubSubMessageToDeviceAdapter pubSubMessageToDeviceAdapter;


    @PostConstruct
    public void asyncSubscribing() {
        var subscriptionName = ProjectSubscriptionName.of(projectId, subscription);

        MessageReceiver receiver = (pubsubMessage, consumer) -> {
            var device = pubSubMessageToDeviceAdapter.adapt(pubsubMessage);
            deviceDataCategorizer.categorize(device);
            consumer.ack();
        };

        var subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
        subscriber.startAsync().awaitRunning();
        subscriber.awaitTerminated();
    }


}
