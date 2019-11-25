package org.usfirst.frc5409.Testrobot.navx;

/**
 * Register Map of NavX.
 * 
 * Some addresses are missing in this implementation, visit this link for complete list.
 * {@link https://pdocs.kauailabs.com/navx-mxp/advanced/register-protocol/} 
 */
public final class RegisterMap {
    private static Regs r(int rx, int ex) {
        return new Regs((byte) rx, (byte) ex);
    }

    public static final byte REG_WHOAMI          = 0x00; //Who Am I (0x32)
    public static final byte REG_BOARD_REV       = 0x01; //Board Revision
    public static final byte REG_FIRM_MAJOR      = 0x02; //Firmware Major Version
    public static final byte REG_FIRM_MINOR      = 0x03; //Firmware Minor Version
    public static final byte REG_UPDATE_RATE     = 0x04; //Update Rate (Hz, [4-200])

    public static final byte REG_OP_STATUS       = 0x08; //Operational Status, see BoardStatus.OP
    public static final byte REG_CAL_STATUS      = 0x09; //Calibration Status, see BoardStatus.CAL
    public static final byte REG_SELFTEST_STATUS = 0x0A; //Self-test Status, see BoardStatus.SELFTEST
    public static final byte REG_SENSOR_STATUS   = 0x10; //Sensor Status, see BoardStatus.SENSOR 

                                                         //Yaw, Pitch, Roll, Heading data
    public static final Regs REGS_YPRH_DATA      = r(0x16, 0x1D); 
                                                         //Gyro Quaternion (W,X,Y,Z) data
    public static final Regs REGS_QWXYZ_DATA     = r(0x2A, 0x31); 
                                                         //Calibrated Gyro (X,Y,Z) data
    public static final Regs REGS_GXYZ_DATA      = r(0x34, 0x39);

    public static final byte REG_INTEGRATION_CTL = 0x56; //Integration Control

                                                         //Integrated Velocity (X,Y,Z)
    public static final Regs REGS_IVXYZ          = r(0x58, 0x63); 
                                                         //Integrated Displacement (X,Y,Z)
    public static final Regs REGS_IDXYZ          = r(0x64, 0x6F); 
}