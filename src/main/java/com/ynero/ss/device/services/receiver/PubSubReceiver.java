package com.ynero.ss.device.services.receiver;

import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
@Log4j2
public class PubSubReceiver {

    private Subscriber subscriber;

    public void startPubSubSubscriber(String subscription, MessageReceiver messageReceiver, String projectId) {
        log.debug("starting pub-sub subscriber: sub={}", subscription);
        var subscriptionName = ProjectSubscriptionName.of(projectId, subscription);

        subscriber = Subscriber.newBuilder(subscriptionName, messageReceiver).build();
        try {
            subscriber.startAsync().awaitRunning(30, SECONDS);
            log.info("pub-sub subscriber started: sub={}", subscription);
        } catch (TimeoutException timeoutException) {
            //after 30s
            log.error("failed to start pub-sub subscriber", timeoutException);
            try {
                subscriber.stopAsync().awaitTerminated(10, SECONDS);
            } catch (TimeoutException e) {
                log.error("failed to shutdown pub-sub subscriber", e);
            }
        }
    }

    public void shutDownPubSubThreadPull() {
        if (subscriber != null) {
            log.info("closing pub-sub subscriber");
            try {
                subscriber.stopAsync().awaitTerminated(15, SECONDS);
            } catch (TimeoutException e) {
                log.error("failed to shutdown pub-sub subscriber", e);
            }
        }
    }
}
