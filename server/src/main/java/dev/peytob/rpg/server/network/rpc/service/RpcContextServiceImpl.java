package dev.peytob.rpg.server.network.rpc.service;

import dev.peytob.rpg.server.network.dto.TokenDto;
import dev.peytob.rpg.server.network.service.AccountAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static dev.peytob.rpg.server.network.rpc.security.Constants.RAW_AUTHORIZATION_TOKEN_CONTEXT_KEY;

@Service
@RequiredArgsConstructor
public class RpcContextServiceImpl implements RpcContextService {

    private final AccountAuthService accountAuthService;

    @Override
    public String getRawAuthenticationToken() {
        return RAW_AUTHORIZATION_TOKEN_CONTEXT_KEY.get();
    }

    @Override
    public TokenDto getAuthenticationTokenData() {
        return accountAuthService.validate(getRawAuthenticationToken()).orElseThrow(IllegalStateException::new);
    }
}
