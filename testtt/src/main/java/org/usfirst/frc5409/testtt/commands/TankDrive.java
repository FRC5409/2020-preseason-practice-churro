/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc5409.testtt.commands;

import org.usfirst.frc5409.testtt.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TankDrive extends Command {
  public TankDrive() {
    // Use requires() here to declare subsystem dependencies
    
  requires(Robot.drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }
  final int LEFT_STICK_Y=1;
    final int  RIGHT_STICK_Y=1;

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
     
    double rightY=Robot.oi.GetDriverRawAxis(RIGHT_STICK_Y);
   

 double leftY= Robot.oi.GetDriverRawAxis(LEFT_STICK_Y);


 Robot.drivetrain.setLeftMotors(LEFT_STICK_Y);
 
 Robot.drivetrain.setRightMotors(RIGHT_STICK_Y);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.drivetrain.setLeftMotors(0);
    Robot.drivetrain.setRightMotors(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
