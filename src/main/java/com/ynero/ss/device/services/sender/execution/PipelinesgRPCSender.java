
package com.ynero.ss.device.services.sender.execution;

import com.ynero.ss.pipeline.dto.proto.PipelinesMessage;
import com.ynero.ss.pipeline.grpc.PipelineQueryReceiverServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class PipelinesgRPCSender {

    @Setter(onMethod_ = {@Value("${execution-service.address.host}")})
    private String executionServiceHost;

    @Setter(onMethod_ = {@Value("${execution-service.address.port}")})
    private int executionServicePort;

    public void send(PipelinesMessage.PipelineQuery request) {
        log.info("request: {}", request);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(executionServiceHost, executionServicePort)
                .usePlaintext()
                .disableRetry()
                .build();
        try {
            var receiverServiceGrpcBlockingStub = PipelineQueryReceiverServiceGrpc.newBlockingStub(channel);
            var result = receiverServiceGrpcBlockingStub.withDeadlineAfter(10, TimeUnit.SECONDS).receive(request);
            log.info(result);
        } catch (Exception ex) {
            log.warn(ex);
        } finally {
            try {
                channel.awaitTermination(20, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.warn(e);
            }
        }

    }
}
