package dev.peytob.rpg.backend.service;

import dev.peytob.rpg.backend.dto.auth.TokenDto;
import dev.peytob.rpg.backend.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TokenUserDetailsService implements UserDetailsService {

    private final AuthProvider authProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByToken(username);
    }

    private UserDetails loadUserByToken(String rawToken) {
        TokenDto token = authProvider.validateRawToken(rawToken)
            .orElseThrow(() -> new UsernameNotFoundException("Token not found or invalid"));

        return toUserDetails(token, rawToken);
    }

    private UserDetails toUserDetails(TokenDto token, String rawToken) {
        return new User(token.username(), rawToken, toSecurityRoles(token.roles()));
    }

    private Collection<? extends GrantedAuthority> toSecurityRoles(Collection<UserRole> roles) {
        return roles.stream().map(UserRole::getGrantedAuthority).toList();
    }
}
