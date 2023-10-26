package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.configuration.properties.AdminProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Providers access token to manage realms, realms roles and users.
 * For now allows only one global admin.
 */
@Service
@RequiredArgsConstructor
public class AdminTokenUserDetailsProvider implements UserDetailsService {

    private final AdminProperties adminProperties;

    @Override
    // Username should be user token.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByTokenValue(username);
    }

    private UserDetails getUserByTokenValue(String tokenValue) throws UsernameNotFoundException {
        if (!adminProperties.token().equals(tokenValue)) {
            throw new UsernameNotFoundException("Admin token is invalid!");
        }

        return new User(adminProperties.token(), tokenValue, Collections.emptyList());
    }
}
