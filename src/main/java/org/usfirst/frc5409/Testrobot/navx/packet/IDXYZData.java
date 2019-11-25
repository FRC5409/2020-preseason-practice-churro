package org.usfirst.frc5409.Testrobot.navx.packet;

/**
 * Integrated Displacement (X Y Z) data container.
 * Packaged from integrated data on NavX MXP.
 */
public class IDXYZData {
    /**
     * Construct IDXYZ data container.
     */
    IDXYZData() {
        dx = 0;
        dy = 0;
        dz = 0;
    }

    /**
     * Construct IDXYZ data container with
     * displacement data parameters.
     * 
     * @param dx Integrated Displacement X
     * @param dy Integrated Displacement Y
     * @param dz Integrated Displacement Z
     */
    IDXYZData(double dx, double dy, double dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    /**
     * Integrated Displacement X
     */
    public double dx;

    /**
     * Integrated Displacement Y
     */
    public double dy;

    /**
     * Integrated Displacement Z
     */
    public double dz;
}