package org.frc.team5409.churro.navx.data;

import org.frc.team5409.churro.navx.NavXData;

/**
 * Integrated Displacement (X Y Z) data container.
 * Packaged from integrated data on NavX MXP.
 * 
 * @author Ketih Davies
 */
public class IDXYZData extends NavXData {
    /**
     * Construct blank IDXYZ data container.
     */
    public IDXYZData() {
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
    public IDXYZData(double dx, double dy, double dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    
    /**
     * {@inheritDoc}
     */
    public static IDXYZData fromRaw(byte raw_data[]) {
        check(raw_data, packet_length);
        return new IDXYZData(
            cv.decodeQ1616(raw_data, 0), // Integrated Displacement X
            cv.decodeQ1616(raw_data, 4), // Integrated Displacement Y
            cv.decodeQ1616(raw_data, 8)  // Integrated Displacement Z
        );
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

    /**
     * {@inheritDoc}
     */
    public static final int packet_length = 12;
}