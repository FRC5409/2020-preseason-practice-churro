/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    
    //color sensor
    public static int NEO550_ID = 11;
    //motor
    public static double maxRPM = 11000;
    
    //need to be changed (PID Values)
    public static double kP = 5e-5;
    public static double kI = 1e-6;
    public static double kD = 0; 
    public static double kIz = 0; 
    public static double kFF = 0;
    public static double kMaxOutput = 1; 
    public static double kMinOutput = -1; 
  
    //PID position control (Rotation)
    public static double gearRatio = 12;
    public static double radiusSmallWheel = 1;
    public static double radiusBigWheel = 16;
    public static double rotationBigWheel = 3.1;
    public static double numberMotorSpinning = rotationBigWheel * gearRatio * radiusBigWheel / radiusSmallWheel;

    //PID velocity control (Position)
    public static double Velocity = 10;      // 10" / sec around the circumference
    public static double motorPositionRPM = Velocity * 60 / (radiusBigWheel*2*3.14) * (radiusBigWheel / radiusSmallWheel) * gearRatio; //RPM of motor spinning for constant velocity
   

}
