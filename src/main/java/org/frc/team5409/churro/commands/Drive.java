package org.frc.team5409.churro.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.XboxController;

import org.frc.team5409.churro.util.JoystickType;

import org.frc.team5409.churro.subsystems.Drivetrain;
import org.frc.team5409.churro.Robot;

public class Drive extends CommandBase {
    //private UserInputService m_input;

    private Drivetrain m_drive;

    public Drive() {
        //m_input = ServiceManager.getService("UserInputService");

        m_drive = Robot.getSubsystem("Drivetrain");

        addRequirements(m_drive);
    }

    @Override
    public void initialize() {
        m_drive.reset();
    }

    @Override
    public void execute() {
        XboxController joystick = Robot.oi.getJoystick(JoystickType.MAIN);
        //
        
        double axis_v = joystick.getRawAxis(3) - joystick.getRawAxis(2);
        double axis_r = joystick.getRawAxis(0);

        m_drive.arcadeDrive(axis_v, axis_r);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_drive.reset();
    }
}
