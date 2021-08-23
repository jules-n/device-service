package com.ynero.ss.device.services.sender;

import com.ynero.ss.pipeline.dto.proto.PipelinesMessage;
import com.ynero.ss.pipeline.grpc.PipelineQueryReceiverServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class PipelinesgRPCSender {

    public void send(PipelinesMessage.PipelineQuery request) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9098)
                .usePlaintext()
                .build();
        var receiverServiceGrpcBlockingStub = PipelineQueryReceiverServiceGrpc.newBlockingStub(channel);
        receiverServiceGrpcBlockingStub.receive(request);
        channel.shutdown();
    }
}
