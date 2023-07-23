package dev.peytob.rpg.client.module.base.service.network.grpc.model.system;

public record ServerConnectionDetails(
    String name,
    String address,
    Integer port
) {
}
