package com.ynero.ss.device.config;

import com.ynero.ss.device.services.receiver.PipelineDropPubSubMessageReceiver;
import com.ynero.ss.device.services.receiver.PipelineResultPubSubMessageReceiver;
import com.ynero.ss.device.services.receiver.PubSubReceiver;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class PipelineResultReceiverConfig {
    @Setter(onMethod_ = {@Value("${spring.cloud.stream.bindings.input.destination.es.result}")})
    private String subscription;

    @Setter(onMethod_ = {@Value("${spring.cloud.gcp.project-id}")})
    private String projectId;

    @Setter(onMethod_ = {@Autowired})
    private PipelineResultPubSubMessageReceiver pipelineResultPubSubMessageReceiver;

    private PubSubReceiver receiver;

    @PostConstruct
    public void startPubSubSubscriber() {
        receiver = new PubSubReceiver();
        receiver.startPubSubSubscriber(subscription, pipelineResultPubSubMessageReceiver, projectId);
    }

    @PreDestroy
    public void shutDownPubSubThreadPull() {
        receiver.shutDownPubSubThreadPull();
    }
}
