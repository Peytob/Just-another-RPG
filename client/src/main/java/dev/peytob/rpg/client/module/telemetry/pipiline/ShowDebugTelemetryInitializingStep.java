package dev.peytob.rpg.client.module.telemetry.pipiline;

import dev.peytob.rpg.client.module.telemetry.model.CpuTelemetry;
import dev.peytob.rpg.client.module.telemetry.model.JvmTelemetry;
import dev.peytob.rpg.client.module.telemetry.model.MemoryTelemetry;
import dev.peytob.rpg.client.module.telemetry.model.OperatingSystemTelemetry;
import dev.peytob.rpg.client.module.telemetry.service.TelemetryCollectingService;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ShowDebugTelemetryInitializingStep implements InitializingPipelineStep {

    private final Logger logger = LoggerFactory.getLogger(ShowDebugTelemetryInitializingStep.class);

    private final TelemetryCollectingService telemetryCollectingService;

    public ShowDebugTelemetryInitializingStep(TelemetryCollectingService telemetryCollectingService) {
        this.telemetryCollectingService = telemetryCollectingService;
    }

    @Override
    public void execute() {
        if (!logger.isDebugEnabled()) {
            return;
        }

        CpuTelemetry cpuTelemetry = telemetryCollectingService.collectCpuTelemetry();
        logger.debug("Available CPU cores: {}", cpuTelemetry.availableProcessors());

        MemoryTelemetry memoryTelemetry = telemetryCollectingService.collectMemoryTelemetry();
        logger.debug("Maximal available memory: {} bytes", memoryTelemetry.maxMemory());
        logger.debug("Total available memory: {} bytes", memoryTelemetry.totalMemory());

        OperatingSystemTelemetry operatingSystemTelemetry = telemetryCollectingService.collectOperatingSystemTelemetry();
        logger.debug("OS: {} ({})", operatingSystemTelemetry.name(), operatingSystemTelemetry.version());
        logger.debug("OS arch: {}", operatingSystemTelemetry.arch());

        JvmTelemetry jvmTelemetry = telemetryCollectingService.collectJvmTelemetry();
        logger.debug("Java version: {}", jvmTelemetry.javaVersion());
        logger.debug("Java vendor: {}", jvmTelemetry.javaVendor());
        logger.debug("Java VM name: {}", jvmTelemetry.vmName());
        logger.debug("Java VM vendor: {}",  jvmTelemetry.vmVendor());
        logger.debug("Java VM info: {}", jvmTelemetry.vmInfo());
        logger.debug("Java VM class version: {}", jvmTelemetry.classVersion());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
