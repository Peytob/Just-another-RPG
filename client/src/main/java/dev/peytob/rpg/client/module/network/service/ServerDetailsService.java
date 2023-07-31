package dev.peytob.rpg.client.module.network.service;


import dev.peytob.rpg.client.module.network.model.ServerConnectionDetails;

import java.util.concurrent.Future;

public interface ServerDetailsService {

    Future<ServerConnectionDetails> getServerConnectionDetails();
}
