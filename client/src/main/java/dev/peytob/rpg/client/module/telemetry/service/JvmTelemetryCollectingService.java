package dev.peytob.rpg.client.module.telemetry.service;

import dev.peytob.rpg.client.module.telemetry.model.CpuTelemetry;
import dev.peytob.rpg.client.module.telemetry.model.JvmTelemetry;
import dev.peytob.rpg.client.module.telemetry.model.MemoryTelemetry;
import dev.peytob.rpg.client.module.telemetry.model.OperatingSystemTelemetry;
import org.springframework.stereotype.Service;

@Service
public class JvmTelemetryCollectingService implements TelemetryCollectingService {
    @Override
    public CpuTelemetry collectCpuTelemetry() {
        return new CpuTelemetry(
            Runtime.getRuntime().availableProcessors()
        );
    }

    @Override
    public JvmTelemetry collectJvmTelemetry() {
        return new JvmTelemetry(
            System.getProperty("java.version", "ND"),
            System.getProperty("java.vendor", "ND"),
            System.getProperty("java.vm.name", "ND"),
            System.getProperty("java.vm.vendor", "ND"),
            System.getProperty("java.vm.info", "ND"),
            System.getProperty("java.class.version", "ND")
        );
    }

    @Override
    public OperatingSystemTelemetry collectOperatingSystemTelemetry() {
        return new OperatingSystemTelemetry(
            System.getProperty("os.name", "ND"),
            System.getProperty("os.arch", "ND"),
            System.getProperty("os.version", "ND")
        );
    }

    @Override
    public MemoryTelemetry collectMemoryTelemetry() {
        return new MemoryTelemetry(
            Runtime.getRuntime().maxMemory(),
            Runtime.getRuntime().freeMemory(),
            Runtime.getRuntime().totalMemory()
        );
    }
}
