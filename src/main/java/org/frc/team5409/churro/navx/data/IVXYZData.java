package org.frc.team5409.churro.navx.data;

import org.frc.team5409.churro.navx.NavXData;

/**
 * Integrated Velocity (X Y Z) data container.
 * Packaged from integrated data on NavX MXP.
 * 
 * @author Keith Davies
 */
public class IVXYZData extends NavXData {
    /**
     * Construct blank IVXYZ data container.
     */
    public IVXYZData() {
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
    public IVXYZData(double vx, double vy, double vz) {
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
    }

    /**
     * {@inheritDoc}
     */
    public static IVXYZData fromRaw(byte raw_data[]) {
        check(raw_data, packet_length);
        return new IVXYZData(
            cv.decodeQ1616(raw_data, 0), // Integrated Velocity X
            cv.decodeQ1616(raw_data, 4), // Integrated Velocity Y
            cv.decodeQ1616(raw_data, 8)  // Integrated Velocity Z
        );
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

    /**
     * {@inheritDoc}
     */
    public static final int packet_length = 12;
}