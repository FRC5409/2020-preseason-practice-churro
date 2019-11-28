package org.usfirst.frc5409.Testrobot.navx.data;

import org.usfirst.frc5409.Testrobot.navx.NavXData;

/**
 * Gryoscopic Orientation (Y P R H) data container.
 * Packaged from gyro data on NavX MXP.
 * [Yaw, Pitch, Roll, Heading, Fused Heading]
 * 
 * @author Keith Davies
 */
public class YPRHData extends NavXData {
    /**
     * Construct blank YPRH data container.
     */
    public YPRHData() {
        yaw   = 0;
        ptch  = 0;
        roll  = 0;
        head  = 0;
        fhead = 0;
    }

    /**
     * Construct YPRH data container with
     * gyro data parameters.
     * 
     * @param yaw   Gyro Yaw
     * @param ptch  Gyro Pitch
     * @param roll  Gyro Roll
     * @param head  Gyro Heading
     * @param fhead Gyro Fused Heading
     */
    public YPRHData(double yaw, double ptch, double roll, double head, double fhead) {
        this.yaw = yaw;
        this.ptch = ptch;
        this.roll = roll;
        this.head = head;
        this.fhead = fhead;
    }

    /**
     * {@inheritDoc}
     */
    public static YPRHData fromRaw(byte raw_data[]) {
        check(raw_data, m_raw_data_length);
        return new YPRHData(
            cv.decodeUnsignedHundredths(raw_data, 0), // Gyro Yaw
            cv.decodeUnsignedHundredths(raw_data, 2), // Gyro Pitch
            cv.decodeUnsignedHundredths(raw_data, 4), // Gyro Roll
            cv.decodeUnsignedHundredths(raw_data, 6), // Gyro Heading
            cv.decodeUnsignedHundredths(raw_data, 8)  // Gyro Fused Heading
        );
    }

    /**
     * Gyro Yaw
     */
    public double yaw;

    /**
     * Gyro Pitch
     */
    public double ptch;

    /**
     * Gyro Roll
     */
    public double roll;

    /**
     * Gyro Heading
     */
    public double head;

    /**
     * Gyro Fused Heading
     */
    public double fhead;

    /**
     * {@inheritDoc}
     */
    private static final int m_raw_data_length = 10;
}