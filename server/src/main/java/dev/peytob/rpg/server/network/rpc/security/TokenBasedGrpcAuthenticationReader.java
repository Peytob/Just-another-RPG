package dev.peytob.rpg.server.network.rpc.security;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collections;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

@RequiredArgsConstructor
public class TokenBasedGrpcAuthenticationReader implements GrpcAuthenticationReader {

    private static final Metadata.Key<String> AUTHENTIFICATION_KEY = Metadata.Key.of("Authentication", ASCII_STRING_MARSHALLER);

    @Override
    public Authentication readAuthentication(ServerCall<?, ?> call, Metadata headers) throws AuthenticationException {
        String rawToken = headers.get(AUTHENTIFICATION_KEY);

        if (rawToken == null) {
            return null;
        }

        return new PreAuthenticatedAuthenticationToken(rawToken, null, Collections.emptyList());
    }
}
