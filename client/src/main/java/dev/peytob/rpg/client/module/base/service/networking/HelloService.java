package dev.peytob.rpg.client.module.base.service.networking;

import dev.peytob.rpg.rpc.interfaces.HelloRequest;
import dev.peytob.rpg.rpc.interfaces.TestHelloServiceGrpc.TestHelloServiceBlockingStub;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    private TestHelloServiceBlockingStub myServiceStub;

    public HelloService(TestHelloServiceBlockingStub myServiceStub) {
        this.myServiceStub = myServiceStub;
    }

    public String hello() {
        HelloRequest request = HelloRequest.newBuilder()
                .setName("Petya")
                .build();

        return myServiceStub.sayHello(request).getMessage();
    }
}
