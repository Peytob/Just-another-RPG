package dev.peytob.rpg.server.network.rpc.service;

import org.springframework.stereotype.Service;

@Service
public class RpcContextServiceImpl implements RpcContextService {

    @Override
    public String getAuthenticationToken() {
        return "mock";
    }
}
