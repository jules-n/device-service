package com.ynero.ss.device.config;

import com.ynero.ss.device.services.receiver.EventsPubSubMessageReceiver;
import com.ynero.ss.device.services.receiver.PipelinePubSubMessageReceiver;
import com.ynero.ss.device.services.receiver.PubSubReceiver;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@Log4j2
public class PubSubReceiverConfig {

    @Setter(onMethod_ = {@Autowired}, onParam_ = {@Value("${spring.cloud.stream.bindings.input.destination.er}")})
    private String ERSubscription;

    @Setter(onMethod_ = {@Autowired}, onParam_ = {@Value("${spring.cloud.stream.bindings.input.destination.es}")})
    private String ESSubscription;

    @Setter(onMethod_ = {@Value("${spring.cloud.gcp.project-id}")})
    private String projectId;

    @Setter(onMethod_ = {@Autowired})
    private EventsPubSubMessageReceiver eventsPubSubMessageReceiver;

    @Setter(onMethod_ = {@Autowired})
    private PipelinePubSubMessageReceiver devicesPipelinePubSubMessageReceiver;

    private PubSubReceiver ERPubSubReceiver;
    private PubSubReceiver ESPubSubReceiver;

    @PostConstruct
    public void startPubSubSubscriber() {
        ERPubSubReceiver = new PubSubReceiver();
        ERPubSubReceiver.startPubSubSubscriber(ERSubscription, eventsPubSubMessageReceiver, projectId);

        ESPubSubReceiver = new PubSubReceiver();
        ESPubSubReceiver.startPubSubSubscriber(ESSubscription, devicesPipelinePubSubMessageReceiver, projectId);
    }

    @PreDestroy
    public void shutDownPubSubThreadPull() {
        ERPubSubReceiver.shutDownPubSubThreadPull();
        ESPubSubReceiver.shutDownPubSubThreadPull();
    }
}
