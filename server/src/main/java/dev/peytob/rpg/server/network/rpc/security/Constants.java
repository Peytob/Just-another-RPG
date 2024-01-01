package dev.peytob.rpg.server.network.rpc.security;

import io.grpc.Context;

public enum Constants {;

    public static final Context.Key<String> RAW_AUTHORIZATION_TOKEN_CONTEXT_KEY = Context.key("raw_authorization_token");
}
