package org.frc.team5409.churro.limelight;

/**
 * Led Mode on Limelight.
 * 
 * @author Keith Davies
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