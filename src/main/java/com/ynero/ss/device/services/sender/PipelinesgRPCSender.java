
package com.ynero.ss.device.services.sender;

import com.ynero.ss.pipeline.dto.proto.PipelinesMessage;
import com.ynero.ss.pipeline.grpc.PipelineQueryReceiverServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        receiverServiceGrpcBlockingStub.receive(request);
        channel.shutdown();
    }
}
