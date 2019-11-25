package org.usfirst.frc5409.Testrobot.navx.packet;

/**
 * Calibrated Gyro (X Y Z) data container.
 * Packaged from gyro data on NavX MXP.
 */
public class GXYZData {
    /**
     * Construct GXYZ data container.
     */
    GXYZData() {
        gx = 0;
        gy = 0;
        gz = 0;
    }

    /**
     * Construct GXYZ data container with Gyro
     * data parameters.
     * 
     * 
     * @param gx Calibrated Gyro X
     * @param gy Calibrated Gyro Y
     * @param gz Calibrated Gyro Z
     */
    GXYZData(double gx, double gy, double gz) {
        this.gx = gx;
        this.gy = gy;
        this.gz = gz;
    }

    /**
     * Calibrated Gyro X
     */
    public double gx;

    /**
     * Calibrated Gyro Y
     */
    public double gy;

    /**
     * Calibrated Gyro Z
     */
    public double gz;
}