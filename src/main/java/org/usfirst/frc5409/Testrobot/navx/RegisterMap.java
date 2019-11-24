package org.usfirst.frc5409.Testrobot.navx;

public final class RegisterMap {
    private static final class regs {
        public regs(byte rx, byte ex) {
            this.rx = rx;
            this.nx = ex - rx;
        }
        public final byte rx; //Starting register
        public final int  nx; //Number of registers
    }

    private static regs r(int rx, int ex) {
        return new regs((byte) rx, (byte) ex);
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
    public static final regs REGS_YPRH_DATA      = r(0x16, 0x1D); 
                                                         //Quaternion (W,X,Y,Z) data
    public static final regs REGS_QWXYZ_DATA     = r(0x2A, 0x31); 
                                                         //Calibrated Gyro (X,Y,Z) data
    public static final regs REGS_GXYZ_DATA      = r(0x34, 0x39);

    public static final byte REG_INTEGRATION_CTL = 0x56; //Integration Control

                                                         //Integrated Velocity (X,Y,Z)
    public static final regs REGS_IVXYZ          = r(0x58, 0x63); 
                                                         //Integrated Displacement (X,Y,Z)
    public static final regs REGS_IDXYZ          = r(0x64, 0x6F); 
}