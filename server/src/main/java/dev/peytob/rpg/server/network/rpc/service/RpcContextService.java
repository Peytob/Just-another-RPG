package dev.peytob.rpg.server.network.rpc.service;

import dev.peytob.rpg.server.network.dto.TokenDto;

public interface RpcContextService {

    String getAuthenticationToken();

    TokenDto getAuthenticationTokenData();
}
