package org.usfirst.frc5409.Testrobot.limelight.lltype;

public enum LedMode {
    LED_PIPELINE(0),
    LED_OFF     (1),
    LED_BLINK   (2),
    LED_ON      (3);

    LedMode(double value) {
        this.value = value;
    }

    public double get() {
        return value;
    }

    private final double value;
}