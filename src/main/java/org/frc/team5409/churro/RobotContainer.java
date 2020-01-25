/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc.team5409.churro;
import edu.wpi.first.wpilibj.Joystick;

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
public class RobotContainer {
//==========================================================================
//  Joysticks
    private Joystick      jMainDriver;
    private Joystick      jSecondaryDriver;

//==========================================================================
//  Subsystems
    private TurretControl sTurretControl;
    private FeederControl sFeederControl;
    private Limelight     sLimelight;
    private Drivetrain    sDrivetrain;

//==========================================================================
//  Commands

    private AlignTurret   cAlignTurret;
    private Drive         cDrive;
    private RunFeeder     cRunFeeder;

    protected void initialize() {
        jMainDriver = new Joystick(0);
        jSecondaryDriver = new Joystick(1);

        sTurretControl = new TurretControl();
        sFeederControl = new FeederControl();
        sLimelight = new Limelight();
        sDrivetrain = new Drivetrain();

        cAlignTurret = new AlignTurret();
        cDrive = new Drive(sDrivetrain);
        cRunFeeder = new RunFeeder();
    }
}
