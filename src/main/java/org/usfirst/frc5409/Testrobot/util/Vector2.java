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

    public Vector2 mult(double sc) {
        return new Vector2(x*sc, y*sc);
    }

    public double distance(Vector2 other) {
        return Math.sqrt(Math.pow(x-other.x, 2) + Math.pow(y-other.y, 2));
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(x+other.x, y+other.y);
    }

    public Vector2 sub(Vector2 other) {
        return new Vector2(x-other.x, y-other.y);
    }

    public Vector2 norm() {
        final double mag = magnitude();
        return new Vector2(x/mag, y/mag);
    }

    public Vector2 apply(double m1, double m2, double m3, double m4) {
        return new Vector2(x*m1 + y*m2, x*m3 + y*m4);
    }
}
