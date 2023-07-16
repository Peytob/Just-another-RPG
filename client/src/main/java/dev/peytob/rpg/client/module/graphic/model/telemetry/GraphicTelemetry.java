package dev.peytob.rpg.client.module.graphic.model.telemetry;

public record GraphicTelemetry(
    String apiName,
    String apiVersion,
    String vendor,
    String renderer,
    String shadingLanguageVersion
) {
}
