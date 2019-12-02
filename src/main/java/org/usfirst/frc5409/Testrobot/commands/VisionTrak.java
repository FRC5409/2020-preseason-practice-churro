package org.usfirst.frc5409.Testrobot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc5409.Testrobot.Robot;
import org.usfirst.frc5409.Testrobot.ada.UNAlgo;
import org.usfirst.frc5409.Testrobot.limelight.LedMode;
import org.usfirst.frc5409.Testrobot.limelight.TrackMatrix;
import org.usfirst.frc5409.Testrobot.util.JoystickType;
import org.usfirst.frc5409.Testrobot.util.Vector2;

public class VisionTrak extends Command {
    UNAlgo algo;

    public VisionTrak() {
        super("VisionTrak");

        algo = new UNAlgo(0, 0, 0, 1, 0);
        requires(Robot.drivetrain);
        requires(Robot.limelight);
        requires(Robot.navX);
    }

    @Override
    protected void initialize() {
        SmartDashboard.putNumber("kD", 0);
        SmartDashboard.putNumber("kR", 0);
        SmartDashboard.putNumber("wd", 0);

        Robot.limelight.setLedMode(LedMode.LED_ON);
        Robot.drivetrain.reset();
        Robot.navX.zeroYaw();
    }

    @Override
    protected void execute() {
        algo.kD = SmartDashboard.getNumber("kD", 0);
        algo.kD = SmartDashboard.getNumber("kR", 0);
        algo.wd = SmartDashboard.getNumber("wd", 0);

        if (Robot.limelight.hasTarget()) {
            TrackMatrix tm = Robot.limelight.getCameraTrack();
            double y_n = tm.yaw/180 * Math.PI; //Asuuming the rotation is not in radians

            double mo[] = algo.compute(
                new Vector2(0,0),
                new Vector2(0,-1),
                new Vector2(tm.x, tm.z),
                new Vector2(Math.cos(y_n), Math.sin(y_n)));

            Robot.drivetrain.tankDrive(mo[0], mo[1]);
        } else
            Robot.drivetrain.reset();
    }

    @Override
    protected boolean isFinished() {
        return Robot.oi.getJoystick(JoystickType.MAIN).getAButton();
    }

    @Override
    protected void end() {
        Robot.limelight.setLedMode(LedMode.LED_OFF);
        Robot.drivetrain.reset();
        Robot.navX.zeroYaw();
    }

    @Override
    protected void interrupted() {
        Robot.limelight.setLedMode(LedMode.LED_OFF);
        Robot.drivetrain.reset();
        Robot.navX.zeroYaw();
    }
}
