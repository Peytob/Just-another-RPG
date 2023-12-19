package dev.peytob.rpg.server.network.configuration;

import dev.peytob.rpg.server.network.rpc.security.TokenBasedGrpcAuthenticationReader;
import dev.peytob.rpg.server.network.service.TokenAuthenticationProvider;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;

import java.util.Collections;

@Configuration
public class GrpcSecurityConfiguration {

    @Bean
    public GrpcAuthenticationReader grpcAuthenticationReader() {
        return new TokenBasedGrpcAuthenticationReader();
    }

    @Bean
    public AuthenticationManager authenticationManager(TokenAuthenticationProvider tokenAuthenticationProvider) {
        return new ProviderManager(Collections.singletonList(tokenAuthenticationProvider));
    }
}
