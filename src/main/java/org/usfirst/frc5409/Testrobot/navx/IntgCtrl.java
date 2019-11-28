package org.usfirst.frc5409.Testrobot.navx;

/**
 * Intergration Control flags
 */
public final class IntgCtrl {
    public static final byte RESET_VEL_X  = 0x01;
    public static final byte RESET_VEL_Y  = 0x02;
    public static final byte RESET_VEL_Z  = 0x04;
    public static final byte RESET_DISP_X = 0x08;
    public static final byte RESET_DISP_Y = 0x10;
    public static final byte RESET_DISP_Z = 0x20;
    public static final byte RESET_YAW	  =-0x80;