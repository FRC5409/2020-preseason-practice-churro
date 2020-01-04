package org.frc.team5409.churro.util;

/**
 * Simple 3D Cartesian plane
 * point / vector / coordinate.
 */
public class Vec3 {
    /**
     * Construct Blank Vec3.
     */
    public Vec3() {
        x = 0;
        y = 0;
        z = 0;
    }

    /**
     * Construct Vec3 with coordinates.
     * 
     * @param x X Coordinate
     * @param y Y Coordinate
     * @param z Z Coordinate
     */
    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * X Coordinate
     */
    public double x;
    
    /**
     * Y Coordinate
     */
    public double y;
    
    /**
     * Z Coordinate
     */
    public double z;
}

