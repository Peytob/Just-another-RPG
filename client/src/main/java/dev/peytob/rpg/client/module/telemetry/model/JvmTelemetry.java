package dev.peytob.rpg.client.module.telemetry.model;

public record JvmTelemetry(
    String javaVersion,
    String javaVendor,
    String vmName,
    String vmVendor,
    String vmInfo,
    String classVersion
) {
}
