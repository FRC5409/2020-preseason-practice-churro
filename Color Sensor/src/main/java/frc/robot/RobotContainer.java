/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.RotationControlPanel;
import frc.robot.commands.PositionControlPanel;
import frc.robot.subsystems.ControlPanel;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  //private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  //private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  private final ControlPanel m_controlPanel = new ControlPanel();
  private final RotationControlPanel m_RotationControlPanel = new RotationControlPanel(m_controlPanel);
  
  public JoystickButton joystickButton1;
  public static JoystickButton joystickButton2;
  public XboxController operatorStick;

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings

    SmartDashboard.putData( "Control Panel Command", new RotationControlPanel(m_controlPanel));

    configureButtonBindings();
  }

  public XboxController getcontrolStick() {
    return operatorStick;
}
  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */

  private void configureButtonBindings() {

    operatorStick = new XboxController(0);
    //Setting up joystick

    joystickButton1 = new JoystickButton(operatorStick, Button.kA.value);
    joystickButton2 = new JoystickButton(operatorStick, Button.kB.value);
    joystickButton1.whenPressed(new RotationControlPanel(m_controlPanel));
    joystickButton2.whenPressed(new PositionControlPanel(m_controlPanel));

  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  //public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    //return m_autoCommand;
  //}
}
