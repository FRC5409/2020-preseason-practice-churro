package org.usfirst.frc5409.Testrobot.navx.packet;

/**
 * Gryoscopic Quaternion (X Y Z W) data container.
 * Packaged from gyro data on NavX MXP.
 */
public class QXYZWData {
    /**
     * Construct blank QXYZW data container.
     */
    QXYZWData() {
        qx = 0;
        qy = 0;
        qz = 0;
        qw = 0;
    }

    /**
     * Construct QXYZW data container with
     * quaternion data parameters.
     * 
     * @param qx Quaternion X
     * @param qy Quaternion Y
     * @param qz Quaternion Z
     * @param qw Quaternion W
     */
    QXYZWData(double qx, double qy, double qz, double qw) {
        this.qx = qx;
        this.qy = qy;
        this.qz = qz;
        this.qw = qz;
    }

    /**
     * Quaternion X
     */
    public double qx;

    /**
     * Quaternion Y
     */
    public double qy;

    /**
     * Quaternion Z
     */
    public double qz;

    /**
     * Quaternion W
     */
    public double qw;
}