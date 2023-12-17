package dev.peytob.rpg.server.network.rpc.security;

import dev.peytob.rpg.server.network.dto.TokenDto;
import dev.peytob.rpg.server.network.service.AccountAuthService;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.Status;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.time.Instant;
import java.util.Collection;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

@RequiredArgsConstructor
public class TokenBasedGrpcAuthenticationReader implements GrpcAuthenticationReader {

    private static final Metadata.Key<String> AUTHENTIFICATION_KEY = Metadata.Key.of("Authentication", ASCII_STRING_MARSHALLER);

    private final AccountAuthService accountAuthService;

    @Override
    public Authentication readAuthentication(ServerCall<?, ?> call, Metadata headers) throws AuthenticationException {
        String rawToken = headers.get(AUTHENTIFICATION_KEY);

        if (rawToken == null) {
            throw new BadCredentialsException("Authentication token not found in headers");
        }

        TokenDto tokenDto = accountAuthService.validate(rawToken)
                .orElseThrow(() -> new BadCredentialsException("Backend validation is failed"));
        
        if (Instant.now().isAfter(tokenDto.tokenExpiredAt())) {
            throw new CredentialsExpiredException("Token expired");
        }

        return new PreAuthenticatedAuthenticationToken(rawToken, null, UserRole.toSecurityRoles(tokenDto.roles()));
    }
}
