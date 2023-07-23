package dev.peytob.rpg.client.module.base.service.network.grpc.system;


import dev.peytob.rpg.client.module.base.service.network.grpc.model.system.ServerConnectionDetails;

import java.util.concurrent.Future;

public interface ServerDetailsService {

    Future<ServerConnectionDetails> getServerConnectionDetails();
}
