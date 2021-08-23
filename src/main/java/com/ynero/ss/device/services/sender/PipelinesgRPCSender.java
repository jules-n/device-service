package com.ynero.ss.device.sender;


import com.ynero.ss.device_service.proto.main.grpc.PipelineRequestGrpc;
import com.ynero.ss.device_service.proto.main.java.PipelinesMessage;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class PipelinesgRPCSender{

    public void send(PipelinesMessage.PipelineQuery request) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9098)
                .usePlaintext()
                .build();
        PipelineRequestGrpc.PipelineRequestBlockingStub stub = PipelineRequestGrpc.newBlockingStub(channel);
        stub.receive(request);
        channel.shutdown();
    }
}
