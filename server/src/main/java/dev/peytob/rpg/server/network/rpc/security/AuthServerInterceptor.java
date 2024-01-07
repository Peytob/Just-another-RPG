package dev.peytob.rpg.server.network.rpc.security;

import io.grpc.ServerInterceptor;

/**
 * Created as marker-interface instead of AuthenticatingServerInterceptor, because current server implementation is not
 * based on Spring security mechanism.
 */
public interface AuthServerInterceptor extends ServerInterceptor {
}
