package org.usfirst.frc5409.Testrobot.limelight.lltype;

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