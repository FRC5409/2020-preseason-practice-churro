/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc.team5409.churro;

import java.util.Set;

import org.frc.team5409.churro.commands.AlignTurret;
import org.frc.team5409.churro.subsystems.FeederControl;
import org.frc.team5409.churro.subsystems.TurretControl;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    public TurretControl sys_turretControl;
    public FeederControl sys_feederControl;
    
    public AlignTurret   cmd_alignTurret;

    public RobotContainer() {
        sys_turretControl = new TurretControl();
        sys_feederControl = new FeederControl();

        cmd_alignTurret = new AlignTurret();
    }
}
