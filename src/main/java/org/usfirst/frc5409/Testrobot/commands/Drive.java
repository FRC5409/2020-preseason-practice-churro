package org.usfirst.frc5409.Testrobot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc5409.Testrobot.subsystems.Drivetrain;
import org.usfirst.frc5409.Testrobot.util.JoystickType;
import org.usfirst.frc5409.Testrobot.Robot;

public class Drive extends Command {

    public Drive() {
        super("Drive");
        requires(Robot.drivetrain);
    }

    @Override
    protected void initialize() {
        Robot.drivetrain.setVelocity(0, 0);
    }

    @Override
    protected void execute() {
        XboxController joystick = Robot.oi.getJoystick(JoystickType.MAIN);
        final double axis_left  = joystick.getRawAxis(2);
        final double axis_right = joystick.getRawAxis(4);

        Robot.drivetrain.setVelocity(axis_left, axis_right);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.drivetrain.setVelocity(0, 0);
    }

    @Override
    protected void interrupted() {
        Robot.drivetrain.setVelocity(0, 0);
    }
}
