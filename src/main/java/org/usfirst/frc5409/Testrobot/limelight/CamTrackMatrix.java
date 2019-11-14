package org.usfirst.frc5409.Testrobot.limelight;

import org.usfirst.frc5409.Testrobot.util.Vector2;
import org.usfirst.frc5409.Testrobot.util.Vector3;

public final class CamTrackMatrix {
    public double x, y, z;
    public double yaw, pitch, roll;

    public CamTrackMatrix() {
        x     = 0;
        y     = 0;
        z     = 0;
        yaw   = 0;
        roll  = 0;
        pitch = 0;
    }

    public CamTrackMatrix(double x, double y, double z, double yaw, double pitch, double roll) {
        this.x     = x;
        this.y     = y;
        this.z     = z;
        this.yaw   = yaw;
        this.roll  = roll;
        this.pitch = pitch;
    }

    public Vector3 getVectPos() {
        return new Vector3(x, y, z);
    }

    public Vector2 getVectNormal() {
        return new Vector2(0,0 ); //Implement this later
    }
}