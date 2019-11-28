package org.usfirst.frc5409.Testrobot.navx.data;

import org.usfirst.frc5409.Testrobot.navx.NavXData;

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
    public static IVXYZData fromRaw(byte raw_data[]) {
        check(raw_data, m_raw_data_length);
        return new IVXYZData(
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
    private static final int m_raw_data_length = 12;
}