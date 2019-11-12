package org.usfirst.frc5409.Testrobot.commands;

import org.usfirst.frc5409.Testrobot.util.JoystickType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc5409.Testrobot.Robot;

public class Drive extends Command {

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
