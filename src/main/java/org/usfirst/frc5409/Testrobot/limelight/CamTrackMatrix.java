package org.usfirst.frc5409.Testrobot.limelight;


public final class CamTrackMatrix {
    public double x, y, z;
    public double yaw, pitch, roll;

    CamTrackMatrix() {
        x     = 0;
        y     = 0;
        z     = 0;
        yaw   = 0;
        roll  = 0;
        pitch = 0;
    }

    CamTrackMatrix(double x, double y, double z, double yaw, double pitch, double roll) {
        this.x     = x;
        this.y     = y;
        this.z     = z;
        this.yaw   = yaw;
        this.roll  = roll;
        this.pitch = pitch;
    }
}