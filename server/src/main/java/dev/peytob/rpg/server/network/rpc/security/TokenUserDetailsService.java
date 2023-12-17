package dev.peytob.rpg.server.network.rpc.security;

import dev.peytob.rpg.server.network.dto.TokenDto;
import dev.peytob.rpg.server.network.service.AccountAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenUserDetailsService implements UserDetailsService {

    private final AccountAuthService accountAuthService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByToken(username);
    }

    private UserDetails loadUserByToken(String rawToken) {
        TokenDto token = accountAuthService.validate(rawToken)
            .orElseThrow(() -> new UsernameNotFoundException("Invalid token"));

        return toUserDetails(token, rawToken);
    }

    private UserDetails toUserDetails(TokenDto token, String rawToken) {
        return new User(token.username(), rawToken, UserRole.toSecurityRoles(token.roles()));
    }
}
