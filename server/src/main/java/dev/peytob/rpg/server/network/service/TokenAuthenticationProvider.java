package dev.peytob.rpg.server.network.service;

import dev.peytob.rpg.server.network.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static dev.peytob.rpg.server.network.rpc.security.UserRole.toSecurityRoles;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final AccountAuthService accountAuthService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken) {
            String rawToken = preAuthenticatedAuthenticationToken.getPrincipal().toString();

            if (rawToken == null) {
                throw new BadCredentialsException("Authentication token not found in headers");
            }

            TokenDto tokenDto = accountAuthService.validate(rawToken)
                .orElseThrow(() -> new BadCredentialsException("Backend validation is failed"));

            if (Instant.now().isAfter(tokenDto.tokenExpiredAt())) {
                throw new CredentialsExpiredException("Token expired");
            }

            return new PreAuthenticatedAuthenticationToken(rawToken, null, toSecurityRoles(tokenDto.roles()));
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
