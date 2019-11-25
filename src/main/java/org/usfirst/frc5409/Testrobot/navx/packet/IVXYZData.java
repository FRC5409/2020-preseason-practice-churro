package org.usfirst.frc5409.Testrobot.navx.packet;

/**
 * Integrated Velocity (X Y Z) data container.
 * Packaged from integrated data on NavX MXP.
 */
public class IVXYZData {
    /**
     * Construct IVXYZ data container.
     */
    IVXYZData() {
        vx = 0;
        vy = 0;
        vz = 0;
    }

    /**
     * Construct IVXYZ data container with
     * displacement data parameters.
     * 
     * @param vx Integrated Velocity X
     * @param vy Integrated Velocity Y
     * @param vz Integrated Velocity Z
     */
    IVXYZData(double vx, double vy, double vz) {
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
    }

    /**
     * Integrated Velocity X
     */
    public double vx;

    /**
     * Integrated Velocity Y
     */
    public double vy;

    /**
     * Integrated Velocity Z
     */
    public double vz;
}