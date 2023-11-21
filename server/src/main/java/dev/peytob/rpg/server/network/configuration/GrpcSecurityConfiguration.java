package dev.peytob.rpg.server.network.configuration;

import dev.peytob.rpg.server.network.rpc.security.TokenBasedGrpcAuthenticationReader;
import dev.peytob.rpg.server.network.service.AccountAuthService;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcSecurityConfiguration {

    @Bean
    public GrpcAuthenticationReader grpcAuthenticationReader(AccountAuthService accountAuthService) {
        return new TokenBasedGrpcAuthenticationReader(accountAuthService);
    }
}
