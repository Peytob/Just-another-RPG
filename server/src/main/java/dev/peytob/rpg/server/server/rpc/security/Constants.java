package dev.peytob.rpg.server.server.rpc.security;

import io.grpc.Context;

public enum Constants {;

    public static final Context.Key<String> USER_ID_CONTEXT_KEY = Context.key("client_id");
}
