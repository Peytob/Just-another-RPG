package dev.peytob.rpg.server;

import dev.peytob.rpg.rpc.interfaces.HelloReply;
import dev.peytob.rpg.rpc.interfaces.HelloRequest;
import dev.peytob.rpg.rpc.interfaces.TestHelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class HelloImpl extends TestHelloServiceGrpc.TestHelloServiceImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        String greeting = "Hello, " + request.getName();

        HelloReply response = HelloReply.newBuilder()
                .setMessage(greeting)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
