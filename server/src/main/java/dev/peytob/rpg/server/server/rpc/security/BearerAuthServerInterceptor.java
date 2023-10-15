package dev.peytob.rpg.server.server.rpc.security;

import dev.peytob.rpg.server.base.resource.User;
import dev.peytob.rpg.server.base.service.user.UserService;
import io.grpc.*;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.server.server.rpc.security.Constants.USER_TEXT_ID_CONTEXT_KEY;
import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

@Component
public class BearerAuthServerInterceptor implements AuthServerInterceptor {

    private static final Metadata.Key<String> AUTHENTIFICATION_KEY = Metadata.Key.of("authentication", ASCII_STRING_MARSHALLER);

    private static final ServerCall.Listener<?> EMPTY_LISTENER = new ServerCall.Listener<>() {};

    private static final Status AUTHENTIFICATION_NOT_FOUND_STATUS = Status.UNAUTHENTICATED.withDescription("Authorization token is required for this request");

    private static final Status INVALID_AUTHENTIFICATION_STATUS = Status.UNAUTHENTICATED.withDescription("Authorization token is invalid");

    private final UserService userService;

    public BearerAuthServerInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        String authorizationKey = headers.get(AUTHENTIFICATION_KEY);

        if (authorizationKey == null) {
            call.close(AUTHENTIFICATION_NOT_FOUND_STATUS, headers);
            return (ServerCall.Listener<ReqT>) EMPTY_LISTENER;
        }

        // TODO Create check auth mechanism
        User user = userService.getUserByToken(authorizationKey);

        if (user == null) {
            call.close(INVALID_AUTHENTIFICATION_STATUS, headers);
            return (ServerCall.Listener<ReqT>) EMPTY_LISTENER;
        }

        Context context = Context.current().withValue(USER_TEXT_ID_CONTEXT_KEY, user.id());
        return Contexts.interceptCall(context, call, headers, next);
    }
}
