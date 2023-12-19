package dev.peytob.rpg.backend.service.security;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;
import java.util.function.Supplier;

public class AuthenticatedAsExpressionRoot extends SecurityExpressionRoot {

    public AuthenticatedAsExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public AuthenticatedAsExpressionRoot(Supplier<Authentication> authentication) {
        super(authentication);
    }

    public boolean authenticatedAs(String requestUserId) {
        if (isAuthenticated() && getPrincipal() instanceof UserDetails user) {
            return Objects.equals(user.getUsername(), requestUserId);
        }

        return false;
    }
}
