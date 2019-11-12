package org.usfirst.frc5409.Testrobot.limelight;

//Limelight type
public final class lltype {
    public enum CameraMode {
        MODE_VISION (0),
        MODE_DRIVER (1);

        CameraMode(double value) {
            this.value = value;
        }

        public double get() {
            return value;
        }

        private final double value;
    }

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

        private final double value;
    }
}