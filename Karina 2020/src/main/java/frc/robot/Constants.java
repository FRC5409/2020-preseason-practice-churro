/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    //public   final int DriveConstants=0;

    public static double convert (double value)
    {


return value*0.0254;

    }

    
    public  static  final int kLeftEncoderPorts [] = { 0, 0};

    public  static final boolean kLeftEncoderReversed = false;

    public static final int kRightEncoderPorts [] = { 0, 0};

    public static final boolean kRightEncoderReversed = false;

    public static final double kEncoderDistancePerPulse=0;

    public static final boolean  kGyroReversed=false;

    public static final double ksVolts = convert(1.05);
    public static final double kvVoltSecondsPerMeter =  convert(0.134);
    public static final double kaVoltSecondsSquaredPerMeter =  convert(0.0303);

    
    public static final double kPDriveVel =  convert(1.45);


    public static final double kTrackwidthMeters =  convert(20.3);
    public static final DifferentialDriveKinematics kDriveKinematics =
        new DifferentialDriveKinematics(kTrackwidthMeters);


        public static final double kMaxSpeedMetersPerSecond =  convert(90);
        public static final double kMaxAccelerationMetersPerSecondSquare=  convert(90);


        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;
}
