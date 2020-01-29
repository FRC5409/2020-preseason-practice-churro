/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain2 extends SubsystemBase {
  /**
   * Creates a new DriveTrain2.
   */
  
  private TalonSRX  m_leftDriveFront_talonSRX_C20 = new TalonSRX(20);
  private TalonSRX m_leftDriveRear_talonSRX_C21 = new TalonSRX(21);
 


  private TalonSRX  m_rightDriveFront_talonSRX_C22 = new TalonSRX(22);
  private TalonSRX m_rightDriveRear_talonSRX_C23= new TalonSRX(23);



// The motors on the left side of the drive.
private  SpeedControllerGroup m_leftMotors;


// The motors on the right side of the drive.
private SpeedControllerGroup m_rightMotors ;


// The robot's drive
private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);

// The left-side drive encoder
private final Encoder m_leftEncoder =
new Encoder(Constants.kLeftEncoderPorts[0], Constants.kLeftEncoderPorts[1],
            Constants.kLeftEncoderReversed);

// The right-side drive encoder
private final Encoder m_rightEncoder =
new Encoder(Constants.kRightEncoderPorts[2], Constants.kRightEncoderPorts[3],
            Constants.kRightEncoderReversed);

// The gyro sensor
private final Gyro m_gyro = new ADXRS450_Gyro();

// Odometry class for tracking robot pose
private final DifferentialDriveOdometry m_odometry;

/**
* Creates a new DriveSubsystem.
*/
public DriveTrain2() {
  //m_leftMotors= new SpeedControllerGroup(m_leftDriveFront_talonSRX_C20, m_leftDriveRear_talonSRX_C21);
 // m_rightMotors= new SpeedControllerGroup( m_rightDriveFront_talonSRX_C22,  m_rightDriveRear_talonSRX_C23 );
 m_leftDriveFront_talonSRX_C20.follow(m_leftDriveRear_talonSRX_C21);

 m_leftDriveRear_talonSRX_C21.setInverted(false);

 m_leftDriveFront_talonSRX_C20.setInverted(InvertType.FollowMaster);

 m_rightDriveFront_talonSRX_C22.follow(m_rightDriveRear_talonSRX_C23);

 m_rightDriveRear_talonSRX_C23.setInverted(false);

 m_rightDriveFront_talonSRX_C22.setInverted(InvertType.FollowMaster);


// Sets the distance per pulse for the encoders
m_leftEncoder.setDistancePerPulse(Constants.kEncoderDistancePerPulse);
m_rightEncoder.setDistancePerPulse(Constants.kEncoderDistancePerPulse);

resetEncoders();
m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));
}

@Override
public void periodic() {
// Update the odometry in the periodic block
m_odometry.update(Rotation2d.fromDegrees(getHeading()), m_leftEncoder.getDistance(),
                m_rightEncoder.getDistance());
}

/**
* Returns the currently-estimated pose of the robot.
*
* @return The pose.
*/
public Pose2d getPose() {
return m_odometry.getPoseMeters();
}

/**
* Returns the current wheel speeds of the robot.
*
* @return The current wheel speeds.
*/
public DifferentialDriveWheelSpeeds getWheelSpeeds() {
return new DifferentialDriveWheelSpeeds(m_leftEncoder.getRate(), m_rightEncoder.getRate());
}

/**
* Resets the odometry to the specified pose.
*
* @param pose The pose to which to set the odometry.
*/
public void resetOdometry(Pose2d pose) {
resetEncoders();
m_odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
}

/**
* Drives the robot using arcade controls.
*
* @param fwd the commanded forward movement
* @param rot the commanded rotation
*/
public void arcadeDrive(double fwd, double rot) {
m_drive.arcadeDrive(fwd, rot);
}

/**
* Controls the left and right sides of the drive directly with voltages.
*
* @param leftVolts  the commanded left output
* @param rightVolts the commanded right output
*/
public void tankDriveVolts(double leftVolts, double rightVolts) {
m_leftMotors.setVoltage(leftVolts);
m_rightMotors.setVoltage(-rightVolts);
}

/**
* Resets the drive encoders to currently read a position of 0.
*/
public void resetEncoders() {
m_leftEncoder.reset();
m_rightEncoder.reset();
}

/**
* Gets the average distance of the two encoders.
*
* @return the average of the two encoder readings
*/
public double getAverageEncoderDistance() {
return (m_leftEncoder.getDistance() + m_rightEncoder.getDistance()) / 2.0;
}

/**
* Gets the left drive encoder.
*
* @return the left drive encoder
*/
public Encoder getLeftEncoder() {
return m_leftEncoder;
}

/**
* Gets the right drive encoder.
*
* @return the right drive encoder
*/
public Encoder getRightEncoder() {
return m_rightEncoder;
}

/**
* Sets the max output of the drive.  Useful for scaling the drive to drive more slowly.
*
* @param maxOutput the maximum output to which the drive will be constrained
*/
public void setMaxOutput(double maxOutput) {
m_drive.setMaxOutput(maxOutput);
}

/**
* Zeroes the heading of the robot.
*/
public void zeroHeading() {
m_gyro.reset();
}

/**
* Returns the heading of the robot.
*
* @return the robot's heading in degrees, from 180 to 180
*/
public double getHeading() {
return Math.IEEEremainder(m_gyro.getAngle(), 360) * (Constants.kGyroReversed ? -1.0 : 1.0);
}

/**
* Returns the turn rate of the robot.
*
* @return The turn rate of the robot, in degrees per second
*/
public double getTurnRate() {
return m_gyro.getRate() * (Constants.kGyroReversed ? -1.0 : 1.0);
}
}
  

//   @Override
//   public void periodic() {
//     // This method will be called once per scheduler run
//   }
// }
