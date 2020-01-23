/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc.team5409.churro;

import org.frc.team5409.churro.system.SystemContainer;

import org.frc.team5409.churro.control.ServiceManager;
import org.frc.team5409.churro.services.UserInputService;
import org.frc.team5409.churro.uinput.IController;
import org.frc.team5409.churro.uinput.IController.*;

import org.frc.team5409.churro.subsystems.FeederControl;
import org.frc.team5409.churro.subsystems.Limelight;
import org.frc.team5409.churro.subsystems.TurretControl;
import org.frc.team5409.churro.subsystems.Drivetrain;

import org.frc.team5409.churro.commands.AlignTurret;
import org.frc.team5409.churro.commands.Drive;
import org.frc.team5409.churro.commands.RunFeeder;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer extends SystemContainer {
    protected void initialize() {
        IController controller = ServiceManager.getService("UserInputService", UserInputService.class).getController(Controller.kMainDriver);

        addSubsystem("TurretControl", new TurretControl());
        addSubsystem(    "Limelight", new Limelight());
        addSubsystem("FeederControl", new FeederControl());
        addSubsystem(   "Drivetrain", new Drivetrain()   );

        addCommand(    "AlignTurret", new AlignTurret());
        addCommand(          "Drive", new Drive()      );
        addCommand(      "RunFeeder", new RunFeeder()  );
            controller.getButton(Button.kA).whilePressedLatch(getCommand("RunFeeder"));
    }
}
