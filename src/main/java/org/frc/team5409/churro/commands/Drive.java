package org.frc.team5409.churro.commands;

import org.frc.team5409.churro.util.JoystickType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.XboxController;
import org.frc.team5409.churro.Robot;

public class Drive implements Command {

    public Drive() {
        super("Drive");

        requires(Robot.drivetrain);
    }

    @Override
    protected void initialize() {
        Robot.drivetrain.reset();
    }

    @Override
    protected void execute() {
        XboxController joystick = Robot.oi.getJoystick(JoystickType.MAIN);

        final double axis_vel   = joystick.getRawAxis(3) - joystick.getRawAxis(2);
        final double axis_rot   = joystick.getRawAxis(0);

        Robot.drivetrain.arcadeDrive(axis_vel, axis_rot);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.drivetrain.reset();
    }

    @Override
    protected void interrupted() {
        Robot.drivetrain.reset();
    }
}
