package org.usfirst.frc5409.Testrobot.navx;

public final class BoardStatus {
    public static final class OP {
        public static final byte OP_STATUS_INITIALIZING            = 0x00;
        public static final byte OP_STATUS_SELFTEST_IN_PROGRESS    = 0x01;
        public static final byte OP_STATUS_ERROR                   = 0x02;
        public static final byte OP_STATUS_IMU_AUTOCAL_IN_PROGRESS = 0x03;
        public static final byte OP_STATUS_NORMAL                  = 0x04;
    }

    public static final class SENSOR {
        public static final byte SENSOR_STATUS_MOVING	           = 0x01;
        public static final byte SENSOR_STATUS_YAW_STABLE	       = 0x02;
        public static final byte SENSOR_STATUS_MAG_DISTURBANCE	   = 0x04;
        public static final byte SENSOR_STATUS_ALTITUDE_VALID	   = 0x08;
        public static final byte SENSOR_STATUS_SEALEVEL_PRESS_SET  = 0x10;
        public static final byte SENSOR_STATUS_FUSED_HEADING_VALID = 0x20;
    }

    public static final class CAL {
        public static final byte CAL_STATUS_IMU_CAL_INPROGRESS	   = 0x00;
        public static final byte CAL_STATUS_IMU_CAL_ACCUMULATE	   = 0x01;
        public static final byte CAL_STATUS_IMU_CAL_COMPLETE	   = 0x02;
        public static final byte CAL_STATUS_MAG_CAL_COMPLETE	   = 0x04;
        public static final byte CAL_STATUS_BARO_CAL_COMPLETE	   = 0x08;
    }

    public static final class SELFTEST {
        public static final byte SELFTEST_STATUS_COMPLETE	       = -0x80; //I don't know if this is correct
        public static final byte SELFTEST_RESULT_GYRO_PASSED	   = 0x01;
        public static final byte SELFTEST_RESULT_ACCEL_PASSED	   = 0x02;
        public static final byte SELFTEST_RESULT_MAG_PASSED	       = 0x04;
        public static final byte SELFTEST_RESULT_BARO_PASSED	   = 0x08;
    }
}