package org.usfirst.frc5409.Testrobot.navx;

public final class BoardStatus {
    public static final class OP {
        public static final byte INITIALIZING            = 0x00;
        public static final byte SELFTEST_IN_PROGRESS    = 0x01;
        public static final byte ERROR                   = 0x02;
        public static final byte IMU_AUTOCAL_IN_PROGRESS = 0x03;
        public static final byte NORMAL                  = 0x04;
    }

    public static final class SENSOR {
        public static final byte MOVING	                 = 0x01;
        public static final byte YAW_STABLE	             = 0x02;
        public static final byte MAG_DISTURBANCE	     = 0x04;
        public static final byte ALTITUDE_VALID	         = 0x08;
        public static final byte SEALEVEL_PRESS_SET      = 0x10;
        public static final byte FUSED_HEADING_VALID     = 0x20;
    }

    public static final class CAL {
        public static final byte IMU_CAL_INPROGRESS	     = 0x00;
        public static final byte IMU_CAL_ACCUMULATE	     = 0x01;
        public static final byte IMU_CAL_COMPLETE	     = 0x02;
        public static final byte MAG_CAL_COMPLETE	     = 0x04;
        public static final byte BARO_CAL_COMPLETE       = 0x08;
    }

    public static final class SELFTEST {
        public static final byte COMPLETE	             = -0x80; //I don't know if this is correct
        public static final byte GYRO_PASSED	         = 0x01;
        public static final byte ACCEL_PASSED	         = 0x02;
        public static final byte MAG_PASSED	             = 0x04;
        public static final byte BARO_PASSED	         = 0x08;
    }
}