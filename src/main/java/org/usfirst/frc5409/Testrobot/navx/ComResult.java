package org.usfirst.frc5409.Testrobot.navx;

public enum ComResult {
    RESULT_SUCCESS(true),
    RESULT_FAILED(false),
    RESULT_NOADDRESS(false);

    private ComResult(boolean bool) {
        this.bool = bool;
    }

    public final boolean bool;
}