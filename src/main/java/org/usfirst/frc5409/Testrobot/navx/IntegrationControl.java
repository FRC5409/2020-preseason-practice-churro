package org.usfirst.frc5409.Testrobot.navx;

public final class IntegrationControl {
    public static final byte INT_CTL_RESET_VEL_X  = 0x01;
    public static final byte INT_CTL_RESET_VEL_Y  = 0x02;
    public static final byte INT_CTL_RESET_VEL_Z  = 0x04;
    public static final byte INT_CTL_RESET_DISP_X = 0x08;
    public static final byte INT_CTL_RESET_DISP_Y = 0x10;
    public static final byte INT_CTL_RESET_DISP_Z = 0x20;
    public static final byte INT_CTL_RESET_YAW	  = -0x80; //Could be incorrect
}