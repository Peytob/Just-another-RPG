package dev.peytob.rpg.client.module.telemetry.model;

public record OperatingSystemTelemetry(
    String name,
    String arch,
    String version
) {
}
