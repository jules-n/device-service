package com.ynero.ss.device_service.proto.main.grpc;
import com.ynero.ss.device_service.proto.main.java.PipelinesMessage;
import com.ynero.ss.device_service.proto.main.java.PipelinesService;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: proto/services/pipelines-service.proto")
public final class PipelineRequestGrpc {

  private PipelineRequestGrpc() {}

  public static final String SERVICE_NAME = "PipelineRequest";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<PipelinesMessage.PipelineQuery,
      com.google.protobuf.Empty> METHOD_RECEIVE =
      io.grpc.MethodDescriptor.<PipelinesMessage.PipelineQuery, com.google.protobuf.Empty>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PipelineRequest", "receive"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              PipelinesMessage.PipelineQuery.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.google.protobuf.Empty.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PipelineRequestStub newStub(io.grpc.Channel channel) {
    return new PipelineRequestStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PipelineRequestBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PipelineRequestBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PipelineRequestFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PipelineRequestFutureStub(channel);
  }

  /**
   */
  public static abstract class PipelineRequestImplBase implements io.grpc.BindableService {

    /**
     */
    public void receive(PipelinesMessage.PipelineQuery request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_RECEIVE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_RECEIVE,
            asyncUnaryCall(
              new MethodHandlers<
                PipelinesMessage.PipelineQuery,
                com.google.protobuf.Empty>(
                  this, METHODID_RECEIVE)))
          .build();
    }
  }

  /**
   */
  public static final class PipelineRequestStub extends io.grpc.stub.AbstractStub<PipelineRequestStub> {
    private PipelineRequestStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PipelineRequestStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PipelineRequestStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PipelineRequestStub(channel, callOptions);
    }

    /**
     */
    public void receive(PipelinesMessage.PipelineQuery request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_RECEIVE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PipelineRequestBlockingStub extends io.grpc.stub.AbstractStub<PipelineRequestBlockingStub> {
    private PipelineRequestBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PipelineRequestBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PipelineRequestBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PipelineRequestBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty receive(PipelinesMessage.PipelineQuery request) {
      return blockingUnaryCall(
          getChannel(), METHOD_RECEIVE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PipelineRequestFutureStub extends io.grpc.stub.AbstractStub<PipelineRequestFutureStub> {
    private PipelineRequestFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PipelineRequestFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PipelineRequestFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PipelineRequestFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> receive(
        PipelinesMessage.PipelineQuery request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_RECEIVE, getCallOptions()), request);
    }
  }

  private static final int METHODID_RECEIVE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PipelineRequestImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PipelineRequestImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RECEIVE:
          serviceImpl.receive((PipelinesMessage.PipelineQuery) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class PipelineRequestDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return PipelinesService.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PipelineRequestGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PipelineRequestDescriptorSupplier())
              .addMethod(METHOD_RECEIVE)
              .build();
        }
      }
    }
    return result;
  }
}
