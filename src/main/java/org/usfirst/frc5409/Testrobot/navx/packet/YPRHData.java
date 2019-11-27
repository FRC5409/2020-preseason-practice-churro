package org.usfirst.frc5409.Testrobot.navx.packet;

/**
 * Gryoscopic Orientation (Y P R H) data container.
 * Packaged from gyro data on NavX MXP.
 * [Yaw, Pitch, Roll, Heading, Fused Heading]
 */
public class YPRHData {
    /**
     * Construct blank YPRH data container.
     */
    YPRHData() {
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
    YPRHData(double yaw, double ptch, double roll, double head, double fhead) {
        this.yaw = yaw;
        this.ptch = ptch;
        this.roll = roll;
        this.head = head;
        this.fhead = fhead;
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
}