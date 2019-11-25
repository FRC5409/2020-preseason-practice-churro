package org.usfirst.frc5409.Testrobot.limelight.lltype;

/**
 * Led Mode on Limelight.
 */
public enum LedMode {
    /**
     * Led controlled by pipeline.
     */
    LED_PIPELINE(0),

    /**
     * Led off.
     */
    LED_OFF     (1),

    /**
     * Blink Led.
     */
    LED_BLINK   (2),

    /**
     * Led on.
     */
    LED_ON      (3);

    LedMode(double value) {
        this.value = value;
    }

    public double get() {
        return value;
    }

    /**
     * Internal Led Mode value.
     */
    private final double value;
}