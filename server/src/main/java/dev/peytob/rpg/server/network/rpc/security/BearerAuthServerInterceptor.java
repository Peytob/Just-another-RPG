package dev.peytob.rpg.server.network.rpc.security;

import io.grpc.*;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.server.network.rpc.security.Constants.RAW_AUTHORIZATION_TOKEN_CONTEXT_KEY;
import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

@Component
@GrpcGlobalServerInterceptor
public class BearerAuthServerInterceptor implements AuthServerInterceptor {

    private static final Metadata.Key<String> AUTHENTIFICATION_KEY = Metadata.Key.of("Authentication", ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        String authorizationKey = headers.get(AUTHENTIFICATION_KEY);

        Context context = authorizationKey == null ?
            Context.current() :
            Context.current().withValue(RAW_AUTHORIZATION_TOKEN_CONTEXT_KEY, authorizationKey);

        return Contexts.interceptCall(context, call, headers, next);
    }
}
