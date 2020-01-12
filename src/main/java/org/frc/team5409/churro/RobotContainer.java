/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc.team5409.churro;

import org.frc.team5409.churro.system.SystemContainer;
import org.frc.team5409.churro.subsystems.Drivetrain;
import org.frc.team5409.churro.subsystems.FeederControl;
import org.frc.team5409.churro.subsystems.TurretControl;

import org.frc.team5409.churro.commands.AlignTurret;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer extends SystemContainer {
    public void initialize() {
        addSubsystem("TurretControl", new TurretControl());
        addSubsystem("FeederControl", new FeederControl());
        addSubsystem(   "Drivetrain", new Drivetrain()   );

        addCommand("AlignTurret", new AlignTurret());
    }
}
