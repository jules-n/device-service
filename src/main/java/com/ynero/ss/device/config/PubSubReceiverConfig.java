package com.ynero.ss.device.config;

import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.ynero.ss.device.services.receiver.PubSubMessageReceiver;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
@Log4j2
public class PubSubReceiverConfig {
    private Subscriber subscriber;

    @Setter(onMethod_ = {@Autowired}, onParam_ = {@Value("${spring.cloud.stream.bindings.input.destination}")})
    private String subscription;

    @Setter(onMethod_ = {@Value("${spring.cloud.gcp.project-id}")})
    private String projectId;

    @Setter(onMethod_ = {@Autowired})
    private PubSubMessageReceiver pubSubMessageReceiver;

    @PostConstruct
    public void startPubSubSubscriber() {
        log.debug("starting pub-sub subscriber: sub={}", subscription);
        var subscriptionName = ProjectSubscriptionName.of(projectId, subscription);

        subscriber = Subscriber.newBuilder(subscriptionName, pubSubMessageReceiver).build();
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

    @PreDestroy
    public void shutDownPubSubThreadPull() {
        if (subscriber != null) {
            log.info("closing pub-sub subscriber");
            try {
                subscriber.stopAsync().awaitTerminated(30, SECONDS);
            } catch (TimeoutException e) {
                log.error("failed to shutdown pub-sub subscriber", e);
            }
        }
    }
}
