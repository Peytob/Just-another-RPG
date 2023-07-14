package dev.peytob.rpg.client.module.telemetry.service;

import dev.peytob.rpg.client.module.telemetry.model.CpuTelemetry;
import dev.peytob.rpg.client.module.telemetry.model.JvmTelemetry;
import dev.peytob.rpg.client.module.telemetry.model.MemoryTelemetry;
import dev.peytob.rpg.client.module.telemetry.model.OperatingSystemTelemetry;

public interface TelemetryCollectingService {

    CpuTelemetry collectCpuTelemetry();

    JvmTelemetry collectJvmTelemetry();

    OperatingSystemTelemetry collectOperatingSystemTelemetry();

    MemoryTelemetry collectMemoryTelemetry();
}
