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
        Robot.drivetrain.updateDrive(0, 0);
    }

    @Override
    protected void execute() {
        XboxController joystick = Robot.oi.getJoystick(JoystickType.MAIN);
        final double axis_vel   = joystick.getRawAxis(3) - joystick.getRawAxis(2);
        final double axis_rot   = joystick.getRawAxis(0);
        Robot.drivetrain.updateDrive(axis_vel, axis_rot); //Have to flip params for some reason...
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.drivetrain.updateDrive(0, 0);
    }

    @Override
    protected void interrupted() {
        Robot.drivetrain.updateDrive(0, 0);
    }
}
