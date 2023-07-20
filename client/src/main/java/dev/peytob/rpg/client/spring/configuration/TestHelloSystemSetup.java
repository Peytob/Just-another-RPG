package dev.peytob.rpg.client.spring.configuration;

import dev.peytob.rpg.rpc.interfaces.TestHelloServiceGrpc.TestHelloServiceBlockingStub;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.client.inject.GrpcClientBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@GrpcClientBean(
        clazz = TestHelloServiceBlockingStub.class,
        client = @GrpcClient("test")
)
public class TestHelloSystemSetup {

    @Bean
    TestHelloServiceBlockingStub foobarService(TestHelloServiceBlockingStub blockingStub) {
        return blockingStub;
    }

}
