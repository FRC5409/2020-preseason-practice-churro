/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc5409.MyRobot.commands;

import org.usfirst.frc5409.MyRobot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TankDrive extends Command {
  public TankDrive() {
    // Use requires() here to declare subsystem dependencies
   requires(Robot.driveTrain);
  }
  public static final int LEFT_STICK_Y_Axis=1;
  public static final int RIGHT_STICK_Y_Axis=5;
  public static final double SPEED_MULTIPLIER=1.0;

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //Gets position of left Stick Y axis
    double leftStickY= Robot.oi.GetDriverRawAxis( LEFT_STICK_Y_Axis );
    //Gets position of Right Stick Y axis
    double rightStickY= Robot.oi.GetDriverRawAxis( RIGHT_STICK_Y_Axis);

    //set speed relative to joystick position
    Robot.driveTrain.setLeftMotor(leftStickY*SPEED_MULTIPLIER);
    Robot.driveTrain.setRightMotor(rightStickY*SPEED_MULTIPLIER);
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    //stops motors 
    Robot.driveTrain.setLeftMotor(0);
    Robot.driveTrain.setRightMotor(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
