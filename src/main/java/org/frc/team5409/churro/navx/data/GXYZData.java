package org.frc.team5409.churro.navx.data;

import org.frc.team5409.churro.navx.NavXData;

/**
 * Calibrated Gyro (X Y Z) data container.
 * Packaged from gyro data on NavX MXP.
 * 
 * @author Keith Davies
 */
public class GXYZData extends NavXData {
    /**
     * Construct blank GXYZ data container.
     */
    public GXYZData() {
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
    public GXYZData(double gx, double gy, double gz) {
        this.gx = gx;
        this.gy = gy;
        this.gz = gz;
    }

    /**
     * {@inheritDoc}
     */
    public static GXYZData fromRaw(byte raw_data[]) {
        check(raw_data, packet_length);
        return new GXYZData(
            cv.decodeSignedShort(raw_data, 0), // Calibrated Gyro X
            cv.decodeSignedShort(raw_data, 2), // Calibrated Gyro Y
            cv.decodeSignedShort(raw_data, 4)  // Calibrated Gyro Z
        );
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

    /**
     * {@inheritDoc}
     */
    public static final int packet_length = 6;
}