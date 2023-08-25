package dev.peytob.rpg.server.server.rpc.context;

import dev.peytob.rpg.server.base.resource.User;
import dev.peytob.rpg.server.base.resource.world.entity.Player;

public interface RpcContextService {

    User getAuthUser();

    Player getAuthWorldPlayer();
}
