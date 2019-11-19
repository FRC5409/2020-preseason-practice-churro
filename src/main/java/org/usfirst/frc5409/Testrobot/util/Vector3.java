package org.usfirst.frc5409.Testrobot.util;

public class Vector3 {
    public double x, y, z;

    public Vector3() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 cross(Vector3 other) {
        return new Vector3(//This probably ain't right
            y*other.z - other.y*z,
            other.x*z - x*other.z,
            x*other.y - other.x*y
        );
    }
}

