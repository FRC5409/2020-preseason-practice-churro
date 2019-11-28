package org.usfirst.frc5409.Testrobot.limelight;

/**
 * Pipeline Index on Limelight.
 * 
 * @author Keith Davies
 */
public enum PipelineIndex {
    PIPELINE_0  (0),
    PIPELINE_1  (1),
    PIPELINE_2  (2),
    PIPELINE_3  (3),
    PIPELINE_4  (4),
    PIPELINE_5  (5),
    PIPELINE_6  (6),
    PIPELINE_7  (7),
    PIPELINE_8  (8),
    PIPELINE_9  (9);

    PipelineIndex(double value) {
        this.value = value;
    }

    public double get() {
        return value;
    }

    /**
     * Internal pipeline index.
     */
    private final double value;
}