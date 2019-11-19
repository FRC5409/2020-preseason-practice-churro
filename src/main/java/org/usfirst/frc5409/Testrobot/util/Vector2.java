package org.usfirst.frc5409.Testrobot.util;

public class Vector2 {
    public double x, y;

    public Vector2() {
        x = 0;
        y = 0;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double dot(Vector2 other) {
        return (x*other.x + y*other.y);
    }

    public double dotn(Vector2 other) {
        return (x*other.x + y*other.y)/(magnitude()*other.magnitude());
    }

    public double magnitude() {
        return Math.sqrt(x*x + y*y);
    }
}
