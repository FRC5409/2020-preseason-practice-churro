/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc0.testRobot.commands;

import org.usfirst.frc0.testRobot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TankDrive extends Command {

  // ----- CHANGE WHEN FOUND OUT -----
  final int LEFT_STICK_Y_AXIS = 1, 
           RIGHT_STICK_Y_AXIS = 2;      // find these out later

  // the speed of the motors is the joystick position times this number
  final double SPEED_MULTIPLIER = 1.0;

  public TankDrive() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // get joystick positions
    double leftStickY  = Robot.oi.GetDriverRawAxis(LEFT_STICK_Y_AXIS);
    double rightStickY = Robot.oi.GetDriverRawAxis(RIGHT_STICK_Y_AXIS);

    // set motor speed relative to joystick positions
    Robot.driveTrain.setLeftMotorSpeed(leftStickY   * SPEED_MULTIPLIER);
    Robot.driveTrain.setRightMotorSpeed(rightStickY * SPEED_MULTIPLIER);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    // stop motors because motor controllers keep their speed until it is updated again by code.
    //  when isFinished, we need to set motors back to 0 or they wont stop
    Robot.driveTrain.setLeftMotorSpeed(0);
    Robot.driveTrain.setLeftMotorSpeed(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
