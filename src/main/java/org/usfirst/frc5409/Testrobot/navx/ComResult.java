package org.usfirst.frc5409.Testrobot.navx;

public enum ComResult {
    SUCCESS(true),
    FAILED(false),
    BADCRC(false),
    NOADDRESS(false);
    

    private ComResult(boolean bool) {
        this.bool = bool;
    }

    public final boolean bool;
}