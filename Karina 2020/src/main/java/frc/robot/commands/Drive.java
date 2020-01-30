/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain2;

public class Drive extends CommandBase {
  private final DriveTrain2 m_driveTrain;
  

   public double driverRTrigger;
   
   public double driverLTrigger;
   
   public double driverLXAxis;
  /**
   * Creates a new Drive.
   * @param Intake subsystem
   */
  public Drive(DriveTrain2 subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_driveTrain = subsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    //driverRTrigger = m_robotContain.getControllerRawAxis(m_robotContain.driveStick, 3);
    driverRTrigger = RobotContainer.driverJoystick.getRawAxis(5);
    driverLTrigger = RobotContainer.driverJoystick.getRawAxis(4);
    driverLXAxis = RobotContainer.driverJoystick.getX(Hand.kLeft);



  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
