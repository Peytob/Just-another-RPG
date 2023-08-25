package dev.peytob.rpg.server.server.rpc.context;

import dev.peytob.rpg.server.base.resource.User;
import dev.peytob.rpg.server.base.resource.world.entity.Player;
import dev.peytob.rpg.server.base.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static dev.peytob.rpg.server.server.rpc.security.Constants.USER_TEXT_ID_CONTEXT_KEY;

@Service
public class RpcContextServiceImpl implements RpcContextService {

    private static final Logger logger = LoggerFactory.getLogger(RpcContextServiceImpl.class);

    private final UserService userService;

    public RpcContextServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User getAuthUser() {
        String userTextId = USER_TEXT_ID_CONTEXT_KEY.get();

        if (userTextId == null) {
            logger.error("Probably, trying to get auth user id from context without authorization! User text id not found in context.");
            throw new IllegalStateException("Auth data not found in server context");
        }

        User user = userService.getUserById(userTextId);

        if (user == null) {
            logger.error("Authenticated user not found");
            throw new IllegalStateException("Auth user not found");
        }

        return user;
    }

    @Override
    public Player getAuthWorldPlayer() {
        User authUser = getAuthUser();

        Player worldPlayer = authUser.getWorldPlayer();

        if (worldPlayer == null) {
            logger.error("Trying to get world player of user, that current not bind to world player");
            throw new IllegalStateException("Player not found");
        }

        return worldPlayer;
    }
}
