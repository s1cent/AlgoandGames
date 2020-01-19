package netcode;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.processing.Generated(
    value = "by gRPC proto compiler (version 1.26.0)",
    comments = "Source: netcode.proto")
public final class GameComGrpc {

  private GameComGrpc() {}

  public static final String SERVICE_NAME = "netcode.GameCom";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<netcode.Netcode.MatchRequest,
      netcode.Netcode.MatchResponse> getNewMatchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "NewMatch",
      requestType = netcode.Netcode.MatchRequest.class,
      responseType = netcode.Netcode.MatchResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<netcode.Netcode.MatchRequest,
      netcode.Netcode.MatchResponse> getNewMatchMethod() {
    io.grpc.MethodDescriptor<netcode.Netcode.MatchRequest, netcode.Netcode.MatchResponse> getNewMatchMethod;
    if ((getNewMatchMethod = GameComGrpc.getNewMatchMethod) == null) {
      synchronized (GameComGrpc.class) {
        if ((getNewMatchMethod = GameComGrpc.getNewMatchMethod) == null) {
          GameComGrpc.getNewMatchMethod = getNewMatchMethod =
              io.grpc.MethodDescriptor.<netcode.Netcode.MatchRequest, netcode.Netcode.MatchResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "NewMatch"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  netcode.Netcode.MatchRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  netcode.Netcode.MatchResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GameComMethodDescriptorSupplier("NewMatch"))
              .build();
        }
      }
    }
    return getNewMatchMethod;
  }

  private static volatile io.grpc.MethodDescriptor<netcode.Netcode.TurnRequest,
      netcode.Netcode.TurnResponse> getSubmitTurnMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SubmitTurn",
      requestType = netcode.Netcode.TurnRequest.class,
      responseType = netcode.Netcode.TurnResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<netcode.Netcode.TurnRequest,
      netcode.Netcode.TurnResponse> getSubmitTurnMethod() {
    io.grpc.MethodDescriptor<netcode.Netcode.TurnRequest, netcode.Netcode.TurnResponse> getSubmitTurnMethod;
    if ((getSubmitTurnMethod = GameComGrpc.getSubmitTurnMethod) == null) {
      synchronized (GameComGrpc.class) {
        if ((getSubmitTurnMethod = GameComGrpc.getSubmitTurnMethod) == null) {
          GameComGrpc.getSubmitTurnMethod = getSubmitTurnMethod =
              io.grpc.MethodDescriptor.<netcode.Netcode.TurnRequest, netcode.Netcode.TurnResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SubmitTurn"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  netcode.Netcode.TurnRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  netcode.Netcode.TurnResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GameComMethodDescriptorSupplier("SubmitTurn"))
              .build();
        }
      }
    }
    return getSubmitTurnMethod;
  }

  private static volatile io.grpc.MethodDescriptor<netcode.Netcode.GameStateRequest,
      netcode.Netcode.GameStateResponse> getGetGameStateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetGameState",
      requestType = netcode.Netcode.GameStateRequest.class,
      responseType = netcode.Netcode.GameStateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<netcode.Netcode.GameStateRequest,
      netcode.Netcode.GameStateResponse> getGetGameStateMethod() {
    io.grpc.MethodDescriptor<netcode.Netcode.GameStateRequest, netcode.Netcode.GameStateResponse> getGetGameStateMethod;
    if ((getGetGameStateMethod = GameComGrpc.getGetGameStateMethod) == null) {
      synchronized (GameComGrpc.class) {
        if ((getGetGameStateMethod = GameComGrpc.getGetGameStateMethod) == null) {
          GameComGrpc.getGetGameStateMethod = getGetGameStateMethod =
              io.grpc.MethodDescriptor.<netcode.Netcode.GameStateRequest, netcode.Netcode.GameStateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetGameState"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  netcode.Netcode.GameStateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  netcode.Netcode.GameStateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GameComMethodDescriptorSupplier("GetGameState"))
              .build();
        }
      }
    }
    return getGetGameStateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<netcode.Netcode.UserRegistrationRequest,
      netcode.Netcode.UserRegistrationResponse> getUserRegistrationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UserRegistration",
      requestType = netcode.Netcode.UserRegistrationRequest.class,
      responseType = netcode.Netcode.UserRegistrationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<netcode.Netcode.UserRegistrationRequest,
      netcode.Netcode.UserRegistrationResponse> getUserRegistrationMethod() {
    io.grpc.MethodDescriptor<netcode.Netcode.UserRegistrationRequest, netcode.Netcode.UserRegistrationResponse> getUserRegistrationMethod;
    if ((getUserRegistrationMethod = GameComGrpc.getUserRegistrationMethod) == null) {
      synchronized (GameComGrpc.class) {
        if ((getUserRegistrationMethod = GameComGrpc.getUserRegistrationMethod) == null) {
          GameComGrpc.getUserRegistrationMethod = getUserRegistrationMethod =
              io.grpc.MethodDescriptor.<netcode.Netcode.UserRegistrationRequest, netcode.Netcode.UserRegistrationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UserRegistration"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  netcode.Netcode.UserRegistrationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  netcode.Netcode.UserRegistrationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GameComMethodDescriptorSupplier("UserRegistration"))
              .build();
        }
      }
    }
    return getUserRegistrationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<netcode.Netcode.GroupRegistrationRequest,
      netcode.Netcode.GroupRegistrationResponse> getGroupRegistrationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GroupRegistration",
      requestType = netcode.Netcode.GroupRegistrationRequest.class,
      responseType = netcode.Netcode.GroupRegistrationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<netcode.Netcode.GroupRegistrationRequest,
      netcode.Netcode.GroupRegistrationResponse> getGroupRegistrationMethod() {
    io.grpc.MethodDescriptor<netcode.Netcode.GroupRegistrationRequest, netcode.Netcode.GroupRegistrationResponse> getGroupRegistrationMethod;
    if ((getGroupRegistrationMethod = GameComGrpc.getGroupRegistrationMethod) == null) {
      synchronized (GameComGrpc.class) {
        if ((getGroupRegistrationMethod = GameComGrpc.getGroupRegistrationMethod) == null) {
          GameComGrpc.getGroupRegistrationMethod = getGroupRegistrationMethod =
              io.grpc.MethodDescriptor.<netcode.Netcode.GroupRegistrationRequest, netcode.Netcode.GroupRegistrationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GroupRegistration"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  netcode.Netcode.GroupRegistrationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  netcode.Netcode.GroupRegistrationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GameComMethodDescriptorSupplier("GroupRegistration"))
              .build();
        }
      }
    }
    return getGroupRegistrationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<netcode.Netcode.AuthPacket,
      netcode.Netcode.GetUserTokenResponse> getGetUserTokenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetUserToken",
      requestType = netcode.Netcode.AuthPacket.class,
      responseType = netcode.Netcode.GetUserTokenResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<netcode.Netcode.AuthPacket,
      netcode.Netcode.GetUserTokenResponse> getGetUserTokenMethod() {
    io.grpc.MethodDescriptor<netcode.Netcode.AuthPacket, netcode.Netcode.GetUserTokenResponse> getGetUserTokenMethod;
    if ((getGetUserTokenMethod = GameComGrpc.getGetUserTokenMethod) == null) {
      synchronized (GameComGrpc.class) {
        if ((getGetUserTokenMethod = GameComGrpc.getGetUserTokenMethod) == null) {
          GameComGrpc.getGetUserTokenMethod = getGetUserTokenMethod =
              io.grpc.MethodDescriptor.<netcode.Netcode.AuthPacket, netcode.Netcode.GetUserTokenResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetUserToken"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  netcode.Netcode.AuthPacket.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  netcode.Netcode.GetUserTokenResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GameComMethodDescriptorSupplier("GetUserToken"))
              .build();
        }
      }
    }
    return getGetUserTokenMethod;
  }

  private static volatile io.grpc.MethodDescriptor<netcode.Netcode.SetPseudonymRequest,
      netcode.Netcode.SetPseudonymResponse> getSetGroupPseudonymMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetGroupPseudonym",
      requestType = netcode.Netcode.SetPseudonymRequest.class,
      responseType = netcode.Netcode.SetPseudonymResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<netcode.Netcode.SetPseudonymRequest,
      netcode.Netcode.SetPseudonymResponse> getSetGroupPseudonymMethod() {
    io.grpc.MethodDescriptor<netcode.Netcode.SetPseudonymRequest, netcode.Netcode.SetPseudonymResponse> getSetGroupPseudonymMethod;
    if ((getSetGroupPseudonymMethod = GameComGrpc.getSetGroupPseudonymMethod) == null) {
      synchronized (GameComGrpc.class) {
        if ((getSetGroupPseudonymMethod = GameComGrpc.getSetGroupPseudonymMethod) == null) {
          GameComGrpc.getSetGroupPseudonymMethod = getSetGroupPseudonymMethod =
              io.grpc.MethodDescriptor.<netcode.Netcode.SetPseudonymRequest, netcode.Netcode.SetPseudonymResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SetGroupPseudonym"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  netcode.Netcode.SetPseudonymRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  netcode.Netcode.SetPseudonymResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GameComMethodDescriptorSupplier("SetGroupPseudonym"))
              .build();
        }
      }
    }
    return getSetGroupPseudonymMethod;
  }

  private static volatile io.grpc.MethodDescriptor<netcode.Netcode.SetPseudonymRequest,
      netcode.Netcode.SetPseudonymResponse> getSetUserPseudonymMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetUserPseudonym",
      requestType = netcode.Netcode.SetPseudonymRequest.class,
      responseType = netcode.Netcode.SetPseudonymResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<netcode.Netcode.SetPseudonymRequest,
      netcode.Netcode.SetPseudonymResponse> getSetUserPseudonymMethod() {
    io.grpc.MethodDescriptor<netcode.Netcode.SetPseudonymRequest, netcode.Netcode.SetPseudonymResponse> getSetUserPseudonymMethod;
    if ((getSetUserPseudonymMethod = GameComGrpc.getSetUserPseudonymMethod) == null) {
      synchronized (GameComGrpc.class) {
        if ((getSetUserPseudonymMethod = GameComGrpc.getSetUserPseudonymMethod) == null) {
          GameComGrpc.getSetUserPseudonymMethod = getSetUserPseudonymMethod =
              io.grpc.MethodDescriptor.<netcode.Netcode.SetPseudonymRequest, netcode.Netcode.SetPseudonymResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SetUserPseudonym"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  netcode.Netcode.SetPseudonymRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  netcode.Netcode.SetPseudonymResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GameComMethodDescriptorSupplier("SetUserPseudonym"))
              .build();
        }
      }
    }
    return getSetUserPseudonymMethod;
  }

  private static volatile io.grpc.MethodDescriptor<netcode.Netcode.AbortMatchRequest,
      netcode.Netcode.Nothing> getAbortMatchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AbortMatch",
      requestType = netcode.Netcode.AbortMatchRequest.class,
      responseType = netcode.Netcode.Nothing.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<netcode.Netcode.AbortMatchRequest,
      netcode.Netcode.Nothing> getAbortMatchMethod() {
    io.grpc.MethodDescriptor<netcode.Netcode.AbortMatchRequest, netcode.Netcode.Nothing> getAbortMatchMethod;
    if ((getAbortMatchMethod = GameComGrpc.getAbortMatchMethod) == null) {
      synchronized (GameComGrpc.class) {
        if ((getAbortMatchMethod = GameComGrpc.getAbortMatchMethod) == null) {
          GameComGrpc.getAbortMatchMethod = getAbortMatchMethod =
              io.grpc.MethodDescriptor.<netcode.Netcode.AbortMatchRequest, netcode.Netcode.Nothing>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AbortMatch"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  netcode.Netcode.AbortMatchRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  netcode.Netcode.Nothing.getDefaultInstance()))
              .setSchemaDescriptor(new GameComMethodDescriptorSupplier("AbortMatch"))
              .build();
        }
      }
    }
    return getAbortMatchMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GameComStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GameComStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GameComStub>() {
        @java.lang.Override
        public GameComStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GameComStub(channel, callOptions);
        }
      };
    return GameComStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GameComBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GameComBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GameComBlockingStub>() {
        @java.lang.Override
        public GameComBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GameComBlockingStub(channel, callOptions);
        }
      };
    return GameComBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GameComFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GameComFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GameComFutureStub>() {
        @java.lang.Override
        public GameComFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GameComFutureStub(channel, callOptions);
        }
      };
    return GameComFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class GameComImplBase implements io.grpc.BindableService {

    /**
     */
    public void newMatch(netcode.Netcode.MatchRequest request,
        io.grpc.stub.StreamObserver<netcode.Netcode.MatchResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getNewMatchMethod(), responseObserver);
    }

    /**
     */
    public void submitTurn(netcode.Netcode.TurnRequest request,
        io.grpc.stub.StreamObserver<netcode.Netcode.TurnResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSubmitTurnMethod(), responseObserver);
    }

    /**
     */
    public void getGameState(netcode.Netcode.GameStateRequest request,
        io.grpc.stub.StreamObserver<netcode.Netcode.GameStateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetGameStateMethod(), responseObserver);
    }

    /**
     */
    public void userRegistration(netcode.Netcode.UserRegistrationRequest request,
        io.grpc.stub.StreamObserver<netcode.Netcode.UserRegistrationResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getUserRegistrationMethod(), responseObserver);
    }

    /**
     */
    public void groupRegistration(netcode.Netcode.GroupRegistrationRequest request,
        io.grpc.stub.StreamObserver<netcode.Netcode.GroupRegistrationResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGroupRegistrationMethod(), responseObserver);
    }

    /**
     */
    public void getUserToken(netcode.Netcode.AuthPacket request,
        io.grpc.stub.StreamObserver<netcode.Netcode.GetUserTokenResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetUserTokenMethod(), responseObserver);
    }

    /**
     */
    public void setGroupPseudonym(netcode.Netcode.SetPseudonymRequest request,
        io.grpc.stub.StreamObserver<netcode.Netcode.SetPseudonymResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSetGroupPseudonymMethod(), responseObserver);
    }

    /**
     */
    public void setUserPseudonym(netcode.Netcode.SetPseudonymRequest request,
        io.grpc.stub.StreamObserver<netcode.Netcode.SetPseudonymResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSetUserPseudonymMethod(), responseObserver);
    }

    /**
     */
    public void abortMatch(netcode.Netcode.AbortMatchRequest request,
        io.grpc.stub.StreamObserver<netcode.Netcode.Nothing> responseObserver) {
      asyncUnimplementedUnaryCall(getAbortMatchMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getNewMatchMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                netcode.Netcode.MatchRequest,
                netcode.Netcode.MatchResponse>(
                  this, METHODID_NEW_MATCH)))
          .addMethod(
            getSubmitTurnMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                netcode.Netcode.TurnRequest,
                netcode.Netcode.TurnResponse>(
                  this, METHODID_SUBMIT_TURN)))
          .addMethod(
            getGetGameStateMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                netcode.Netcode.GameStateRequest,
                netcode.Netcode.GameStateResponse>(
                  this, METHODID_GET_GAME_STATE)))
          .addMethod(
            getUserRegistrationMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                netcode.Netcode.UserRegistrationRequest,
                netcode.Netcode.UserRegistrationResponse>(
                  this, METHODID_USER_REGISTRATION)))
          .addMethod(
            getGroupRegistrationMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                netcode.Netcode.GroupRegistrationRequest,
                netcode.Netcode.GroupRegistrationResponse>(
                  this, METHODID_GROUP_REGISTRATION)))
          .addMethod(
            getGetUserTokenMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                netcode.Netcode.AuthPacket,
                netcode.Netcode.GetUserTokenResponse>(
                  this, METHODID_GET_USER_TOKEN)))
          .addMethod(
            getSetGroupPseudonymMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                netcode.Netcode.SetPseudonymRequest,
                netcode.Netcode.SetPseudonymResponse>(
                  this, METHODID_SET_GROUP_PSEUDONYM)))
          .addMethod(
            getSetUserPseudonymMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                netcode.Netcode.SetPseudonymRequest,
                netcode.Netcode.SetPseudonymResponse>(
                  this, METHODID_SET_USER_PSEUDONYM)))
          .addMethod(
            getAbortMatchMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                netcode.Netcode.AbortMatchRequest,
                netcode.Netcode.Nothing>(
                  this, METHODID_ABORT_MATCH)))
          .build();
    }
  }

  /**
   */
  public static final class GameComStub extends io.grpc.stub.AbstractAsyncStub<GameComStub> {
    private GameComStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GameComStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GameComStub(channel, callOptions);
    }

    /**
     */
    public void newMatch(netcode.Netcode.MatchRequest request,
        io.grpc.stub.StreamObserver<netcode.Netcode.MatchResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getNewMatchMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void submitTurn(netcode.Netcode.TurnRequest request,
        io.grpc.stub.StreamObserver<netcode.Netcode.TurnResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSubmitTurnMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getGameState(netcode.Netcode.GameStateRequest request,
        io.grpc.stub.StreamObserver<netcode.Netcode.GameStateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetGameStateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void userRegistration(netcode.Netcode.UserRegistrationRequest request,
        io.grpc.stub.StreamObserver<netcode.Netcode.UserRegistrationResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUserRegistrationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void groupRegistration(netcode.Netcode.GroupRegistrationRequest request,
        io.grpc.stub.StreamObserver<netcode.Netcode.GroupRegistrationResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGroupRegistrationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getUserToken(netcode.Netcode.AuthPacket request,
        io.grpc.stub.StreamObserver<netcode.Netcode.GetUserTokenResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetUserTokenMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void setGroupPseudonym(netcode.Netcode.SetPseudonymRequest request,
        io.grpc.stub.StreamObserver<netcode.Netcode.SetPseudonymResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSetGroupPseudonymMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void setUserPseudonym(netcode.Netcode.SetPseudonymRequest request,
        io.grpc.stub.StreamObserver<netcode.Netcode.SetPseudonymResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSetUserPseudonymMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void abortMatch(netcode.Netcode.AbortMatchRequest request,
        io.grpc.stub.StreamObserver<netcode.Netcode.Nothing> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAbortMatchMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class GameComBlockingStub extends io.grpc.stub.AbstractBlockingStub<GameComBlockingStub> {
    private GameComBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GameComBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GameComBlockingStub(channel, callOptions);
    }

    /**
     */
    public netcode.Netcode.MatchResponse newMatch(netcode.Netcode.MatchRequest request) {
      return blockingUnaryCall(
          getChannel(), getNewMatchMethod(), getCallOptions(), request);
    }

    /**
     */
    public netcode.Netcode.TurnResponse submitTurn(netcode.Netcode.TurnRequest request) {
      return blockingUnaryCall(
          getChannel(), getSubmitTurnMethod(), getCallOptions(), request);
    }

    /**
     */
    public netcode.Netcode.GameStateResponse getGameState(netcode.Netcode.GameStateRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetGameStateMethod(), getCallOptions(), request);
    }

    /**
     */
    public netcode.Netcode.UserRegistrationResponse userRegistration(netcode.Netcode.UserRegistrationRequest request) {
      return blockingUnaryCall(
          getChannel(), getUserRegistrationMethod(), getCallOptions(), request);
    }

    /**
     */
    public netcode.Netcode.GroupRegistrationResponse groupRegistration(netcode.Netcode.GroupRegistrationRequest request) {
      return blockingUnaryCall(
          getChannel(), getGroupRegistrationMethod(), getCallOptions(), request);
    }

    /**
     */
    public netcode.Netcode.GetUserTokenResponse getUserToken(netcode.Netcode.AuthPacket request) {
      return blockingUnaryCall(
          getChannel(), getGetUserTokenMethod(), getCallOptions(), request);
    }

    /**
     */
    public netcode.Netcode.SetPseudonymResponse setGroupPseudonym(netcode.Netcode.SetPseudonymRequest request) {
      return blockingUnaryCall(
          getChannel(), getSetGroupPseudonymMethod(), getCallOptions(), request);
    }

    /**
     */
    public netcode.Netcode.SetPseudonymResponse setUserPseudonym(netcode.Netcode.SetPseudonymRequest request) {
      return blockingUnaryCall(
          getChannel(), getSetUserPseudonymMethod(), getCallOptions(), request);
    }

    /**
     */
    public netcode.Netcode.Nothing abortMatch(netcode.Netcode.AbortMatchRequest request) {
      return blockingUnaryCall(
          getChannel(), getAbortMatchMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class GameComFutureStub extends io.grpc.stub.AbstractFutureStub<GameComFutureStub> {
    private GameComFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GameComFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GameComFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<netcode.Netcode.MatchResponse> newMatch(
        netcode.Netcode.MatchRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getNewMatchMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<netcode.Netcode.TurnResponse> submitTurn(
        netcode.Netcode.TurnRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSubmitTurnMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<netcode.Netcode.GameStateResponse> getGameState(
        netcode.Netcode.GameStateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetGameStateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<netcode.Netcode.UserRegistrationResponse> userRegistration(
        netcode.Netcode.UserRegistrationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUserRegistrationMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<netcode.Netcode.GroupRegistrationResponse> groupRegistration(
        netcode.Netcode.GroupRegistrationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGroupRegistrationMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<netcode.Netcode.GetUserTokenResponse> getUserToken(
        netcode.Netcode.AuthPacket request) {
      return futureUnaryCall(
          getChannel().newCall(getGetUserTokenMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<netcode.Netcode.SetPseudonymResponse> setGroupPseudonym(
        netcode.Netcode.SetPseudonymRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSetGroupPseudonymMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<netcode.Netcode.SetPseudonymResponse> setUserPseudonym(
        netcode.Netcode.SetPseudonymRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSetUserPseudonymMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<netcode.Netcode.Nothing> abortMatch(
        netcode.Netcode.AbortMatchRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getAbortMatchMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_NEW_MATCH = 0;
  private static final int METHODID_SUBMIT_TURN = 1;
  private static final int METHODID_GET_GAME_STATE = 2;
  private static final int METHODID_USER_REGISTRATION = 3;
  private static final int METHODID_GROUP_REGISTRATION = 4;
  private static final int METHODID_GET_USER_TOKEN = 5;
  private static final int METHODID_SET_GROUP_PSEUDONYM = 6;
  private static final int METHODID_SET_USER_PSEUDONYM = 7;
  private static final int METHODID_ABORT_MATCH = 8;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final GameComImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(GameComImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_NEW_MATCH:
          serviceImpl.newMatch((netcode.Netcode.MatchRequest) request,
              (io.grpc.stub.StreamObserver<netcode.Netcode.MatchResponse>) responseObserver);
          break;
        case METHODID_SUBMIT_TURN:
          serviceImpl.submitTurn((netcode.Netcode.TurnRequest) request,
              (io.grpc.stub.StreamObserver<netcode.Netcode.TurnResponse>) responseObserver);
          break;
        case METHODID_GET_GAME_STATE:
          serviceImpl.getGameState((netcode.Netcode.GameStateRequest) request,
              (io.grpc.stub.StreamObserver<netcode.Netcode.GameStateResponse>) responseObserver);
          break;
        case METHODID_USER_REGISTRATION:
          serviceImpl.userRegistration((netcode.Netcode.UserRegistrationRequest) request,
              (io.grpc.stub.StreamObserver<netcode.Netcode.UserRegistrationResponse>) responseObserver);
          break;
        case METHODID_GROUP_REGISTRATION:
          serviceImpl.groupRegistration((netcode.Netcode.GroupRegistrationRequest) request,
              (io.grpc.stub.StreamObserver<netcode.Netcode.GroupRegistrationResponse>) responseObserver);
          break;
        case METHODID_GET_USER_TOKEN:
          serviceImpl.getUserToken((netcode.Netcode.AuthPacket) request,
              (io.grpc.stub.StreamObserver<netcode.Netcode.GetUserTokenResponse>) responseObserver);
          break;
        case METHODID_SET_GROUP_PSEUDONYM:
          serviceImpl.setGroupPseudonym((netcode.Netcode.SetPseudonymRequest) request,
              (io.grpc.stub.StreamObserver<netcode.Netcode.SetPseudonymResponse>) responseObserver);
          break;
        case METHODID_SET_USER_PSEUDONYM:
          serviceImpl.setUserPseudonym((netcode.Netcode.SetPseudonymRequest) request,
              (io.grpc.stub.StreamObserver<netcode.Netcode.SetPseudonymResponse>) responseObserver);
          break;
        case METHODID_ABORT_MATCH:
          serviceImpl.abortMatch((netcode.Netcode.AbortMatchRequest) request,
              (io.grpc.stub.StreamObserver<netcode.Netcode.Nothing>) responseObserver);
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

  private static abstract class GameComBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GameComBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return netcode.Netcode.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("GameCom");
    }
  }

  private static final class GameComFileDescriptorSupplier
      extends GameComBaseDescriptorSupplier {
    GameComFileDescriptorSupplier() {}
  }

  private static final class GameComMethodDescriptorSupplier
      extends GameComBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    GameComMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (GameComGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GameComFileDescriptorSupplier())
              .addMethod(getNewMatchMethod())
              .addMethod(getSubmitTurnMethod())
              .addMethod(getGetGameStateMethod())
              .addMethod(getUserRegistrationMethod())
              .addMethod(getGroupRegistrationMethod())
              .addMethod(getGetUserTokenMethod())
              .addMethod(getSetGroupPseudonymMethod())
              .addMethod(getSetUserPseudonymMethod())
              .addMethod(getAbortMatchMethod())
              .build();
        }
      }
    }
    return result;
  }
}
