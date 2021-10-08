
package com.ynero.ss.device.services.sender.execution;

import com.ynero.ss.pipeline.dto.proto.PipelinesMessage;
import com.ynero.ss.pipeline.grpc.PipelineQueryReceiverServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class PipelinesgRPCSender {

    @Setter(onMethod_ = {@Value("${execution-service.address.host}")})
    private String executionServiceHost;

    @Setter(onMethod_ = {@Value("${execution-service.address.port}")})
    private int executionServicePort;

    public void send(PipelinesMessage.PipelineQuery request) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(executionServiceHost, executionServicePort)
                .usePlaintext()
                .build();
        var receiverServiceGrpcBlockingStub = PipelineQueryReceiverServiceGrpc.newBlockingStub(channel);

        log.info(request);
        var emptyResult = receiverServiceGrpcBlockingStub.receive(request);
        log.info(emptyResult);
        channel.shutdownNow();
    }
}
