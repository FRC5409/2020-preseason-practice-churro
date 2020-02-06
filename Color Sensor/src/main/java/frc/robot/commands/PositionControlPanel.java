/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ControlPanel;
//import frc.robot.Constants;

public class PositionControlPanel extends CommandBase {

  private ControlPanel m_ControlPanel;
  boolean colorFound;
  String searchColor;
  /**
   * Creates a new PositionControlPanel.
   */
  public PositionControlPanel(ControlPanel subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_ControlPanel = subsystem;
    addRequirements(m_ControlPanel);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_ControlPanel.setColorSensor();
    m_ControlPanel.setMotor();
    searchColor = m_ControlPanel.getFMS();

    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_ControlPanel.getColorString() == searchColor) {
      m_ControlPanel.wheelStopSpinning();
      colorFound = true;
    } else {
      colorFound = false;
      m_ControlPanel.pidVelocityControl();
    }

    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (colorFound = true){
      return true;
    } else {
    return false;}
  }
}
