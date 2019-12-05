package org.usfirst.frc5409.Testrobot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc5409.Testrobot.Robot;
import org.usfirst.frc5409.Testrobot.ada.UNAlgo;
import org.usfirst.frc5409.Testrobot.ada.blind.SOPTrack;
import org.usfirst.frc5409.Testrobot.limelight.LedMode;
import org.usfirst.frc5409.Testrobot.limelight.PipelineIndex;
import org.usfirst.frc5409.Testrobot.limelight.TrackMatrix;
import org.usfirst.frc5409.Testrobot.util.JoystickType;
import org.usfirst.frc5409.Testrobot.util.Vector2;

public class VisionTrak extends Command {
    SOPTrack sop_track;
    UNAlgo algo;

    int tracking_state;
    public VisionTrak() {
        super("VisionTrak");

        algo = new UNAlgo(0, 0, 0, 1, 0);
        sop_track = new SOPTrack();
        requires(Robot.drivetrain);
        requires(Robot.limelight);
        requires(Robot.navX);
        SmartDashboard.setDefaultNumber("kD", 0);
        SmartDashboard.setDefaultNumber("kR", 0);
        SmartDashboard.setDefaultNumber("wd", 0);
        SmartDashboard.setDefaultNumber("scale", 1);
        SmartDashboard.setDefaultNumber("Target Area", 20);

        tracking_state = 0;
    }

    @Override
    protected void initialize() {
        //SmartDashboard.putNumber("kD", 0);
        //SmartDashboard.putNumber("kR", 0);
        //SmartDashboard.putNumber("wd", 0);
        //SmartDashboard.putNumber("scale", 0);

        Robot.limelight.setLedMode(LedMode.LED_ON);
        Robot.drivetrain.reset();
        Robot.navX.zeroYaw();
    }

    @Override
    protected void execute() {
        algo.kD = SmartDashboard.getNumber("kD", 0);
        algo.kR = SmartDashboard.getNumber("kR", 0);
        algo.wd = SmartDashboard.getNumber("wd", 0);
        double tarea = SmartDashboard.getNumber("Target Area", 20);
        double scale = SmartDashboard.getNumber("scale", 0);

        if (Robot.limelight.hasTarget()) {
            if (tracking_state != 1) {
                tracking_state = 1;
                sop_track.deInitiate();
            }
            /*TrackMatrix tm = Robot.limelight.getCameraTrack();

            if (tm == null) {
                Robot.drivetrain.reset();
                if (Robot.limelight.getPipelineIndex() == PipelineIndex.PIPELINE_0)
                    Robot.limelight.setPipelineIndex(PipelineIndex.PIPELINE_1);
                else
                    Robot.limelight.setPipelineIndex(PipelineIndex.PIPELINE_0);
                Timer.delay(1.5);
                return;
            }

            double y_n = tm.ptch/180 * Math.PI; //Asuuming the rotation is not in radians

            double mo[] = algo.compute(
                new Vector2(0,0),
                new Vector2(0,-1),
                new Vector2(tm.x, tm.z),
                new Vector2(-Math.sin(y_n), Math.cos(y_n)));

            Robot.drivetrain.tankDrive(mo[0]*scale, mo[1]*scale);

            SmartDashboard.putNumber("Rotation", tm.ptch);
            SmartDashboard.putBoolean("targets", Robot.limelight.hasTarget());
            SmartDashboard.putNumber("ML", mo[0]);
            SmartDashboard.putNumber("MR", mo[1]);*/

            Vector2 target = Robot.limelight.getTarget();
            double area = Robot.limelight.getTargetArea();
            sop_track.feed(target.x);
            double kR = algo.kR*target.x;
            double kD;
            

            kD = algo.kD*(tarea - area);

            double s = Math.max(1, Math.max(Math.abs(kD+kR),Math.abs(kD-kR)));

            Robot.drivetrain.tankDrive( (kD+kR)/s * scale, (kD-kR)/s * scale);
            SmartDashboard.putNumber("Motor Left", (kD+kR)/s * scale);
            SmartDashboard.putNumber("Motor Right", (kD-kR)/s * scale);
        } else {
            if (tracking_state == 1) {//CHANGE THIS TO AN ENUM ASAP
                sop_track.initiate();
                Robot.navX.zeroYaw();
                tracking_state = 2;
            } else if (tracking_state == 2) {
                double kR = algo.kR*(sop_track.track() - Robot.navX.getYPRH().yaw);
                double s = Math.max(1, Math.abs(kR));

                Robot.drivetrain.tankDrive((kR)/s * scale, (-kR)/s * scale);
            } else if (tracking_state == 0) 
                Robot.drivetrain.reset();
        }
            
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
