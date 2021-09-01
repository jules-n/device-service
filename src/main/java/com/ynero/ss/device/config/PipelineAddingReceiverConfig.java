package com.ynero.ss.device.config;

import com.ynero.ss.device.services.receiver.PipelineAddingPubSubMessageReceiver;
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
public class PipelineAddingReceiverConfig {

    @Setter(onMethod_ = {@Autowired}, onParam_ = {@Value("${spring.cloud.stream.bindings.input.destination.es.add}")})
    private String subscription;

    @Setter(onMethod_ = {@Value("${spring.cloud.gcp.project-id}")})
    private String projectId;

    @Setter(onMethod_ = {@Autowired})
    private PipelineAddingPubSubMessageReceiver pipelineAddingPubSubMessageReceiver;

    private PubSubReceiver receiver;

    @PostConstruct
    public void startPubSubSubscriber() {
        receiver = new PubSubReceiver();
        receiver.startPubSubSubscriber(subscription, pipelineAddingPubSubMessageReceiver, projectId);
    }

    @PreDestroy
    public void shutDownPubSubThreadPull() {
        receiver.shutDownPubSubThreadPull();
    }
}
