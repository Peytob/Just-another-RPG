package dev.peytob.rpg.engine.utils;

public enum ExitCode {
    SUCCESS(0),
    FAILED(-1);

    private final int applicationExitCode;

    ExitCode(int applicationExitCode) {
        this.applicationExitCode = applicationExitCode;
    }

    public int getApplicationExitCode() {
        return applicationExitCode;
    }
}
