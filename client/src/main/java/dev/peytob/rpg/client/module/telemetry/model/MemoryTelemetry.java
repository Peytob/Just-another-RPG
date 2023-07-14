package dev.peytob.rpg.client.module.telemetry.model;

public record MemoryTelemetry(
    long maxMemory,
    long freeMemory,
    long totalMemory
) {
}
