package org.frc.team5409.churro.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.frc.team5409.churro.Robot;
import org.frc.team5409.churro.experimental.ada.UNAlgo;
import org.frc.team5409.churro.experimental.ada.blind.SOPTrack;
import org.frc.team5409.churro.experimental.ada.FKBTrack;
import org.frc.team5409.churro.limelight.LedMode;
import org.frc.team5409.churro.limelight.PipelineIndex;
import org.frc.team5409.churro.limelight.TrackMatrix;
import org.frc.team5409.churro.navx.data.IDXYZData;
import org.frc.team5409.churro.util.JoystickType;
import org.frc.team5409.churro.util.Vec2;
import org.frc.team5409.churro.util.Vec3;

public class VisionTrak extends Command {
    SOPTrack sop_track;
    FKBTrack fkb_track;
    UNAlgo algo;

    int tracking_state = 0;

    double h0 = 41;
    double h1 = 22; //inches
    double a0 = (10.0d/180.0d) * Math.PI;
    Vec3 n1 = new Vec3(0, -1,0);
    Vec3 p1 = new Vec3(0,0,0);
    double lost_time = 0;
    double last_lost = 0;
    Vec3 n0 = new Vec3(0,0,0);
    Vec3 p0 = new Vec3(0,0,0);
    IDXYZData last = new IDXYZData();
    public VisionTrak() {
        super("VisionTrak");

        algo = new UNAlgo(0, 0, 0, 1, 0);
        fkb_track = new FKBTrack(26.75);
        sop_track = new SOPTrack();
        requires(Robot.drivetrain);
        requires(Robot.limelight);
        requires(Robot.navX);
        SmartDashboard.setDefaultNumber("Robot height (in)", h0);
        SmartDashboard.setDefaultNumber("Target height (in)", h1);
        SmartDashboard.setDefaultNumber("a0", 10);
        SmartDashboard.setDefaultNumber("Max. lost time (ms)", lost_time);

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
        Robot.drivetrain.enc_q4t_left_drive.reset();
        Robot.drivetrain.enc_q4t_right_drive.reset();
    }

    @Override
    protected void execute() {
        algo.kD = SmartDashboard.getNumber("kD", 0);
        algo.kR = SmartDashboard.getNumber("kR", 0);
        algo.wd = SmartDashboard.getNumber("wd", 0);

        h0 = SmartDashboard.getNumber("Robot height (in)", h0);
        h1 = SmartDashboard.getNumber("Target height (in)", h1);
        a0 = (SmartDashboard.getNumber("a0", 0)/180.0d) * Math.PI;
        lost_time = SmartDashboard.getNumber("Max. lost time (ms)", lost_time);

        Vec3 pos = new Vec3();
        if (Robot.limelight.hasTarget()) {
            if (tracking_state != 1) {
                tracking_state = 1;
                sop_track.deInitiate();
            }
        } else {
            if (tracking_state != 2 && tracking_state != 0) {
                tracking_state = 2;
                Robot.navX.zeroDisplacement();
                sop_track.initiate();
                fkb_track.reset(0, 0, 0);
                last_lost = Timer.getFPGATimestamp();
            }
        }

        if (tracking_state == 1) {
            Vec2 target = Robot.limelight.getTarget();
            Vec2 targett = new Vec2(
                target.x/180 * Math.PI,
                target.y/180 * Math.PI
            );
            double d = Math.abs(h1-h0) / (Math.sin(targett.y+a0)*12);
            
            Vec3 ppos = new Vec3(
                d * (Math.cos(targett.y)*Math.cos(targett.x)),
                d * (Math.cos(targett.y)*(-Math.sin(targett.x))),
                d * Math.sin(targett.y)
            );
            p0 = ppos;
            pos = ppos;
            SmartDashboard.putNumber("distance", d);
        } else if (tracking_state == 2) {
            if (Timer.getFPGATimestamp()-last_lost > lost_time) {
                tracking_state = 0;
            } else {
                double lv =  Robot.drivetrain.enc_q4t_left_drive.getRate();
                double rv =  Robot.drivetrain.enc_q4t_right_drive.getRate();
               fkb_track.feed(lv, rv);
               pos = new Vec3(
                   p0.x + fkb_track.getPosition().x,
                   p0.y + fkb_track.getPosition().y,
                   p0.z
               );
            }
            
        }
        SmartDashboard.putNumber("Left encoder", Robot.drivetrain.enc_q4t_left_drive.getDistance());
        SmartDashboard.putNumber("Right encoder", Robot.drivetrain.enc_q4t_right_drive.getDistance());
        SmartDashboard.putString("position", String.format("[%f, %f, %f]", pos.x, pos.y, pos.z));
        SmartDashboard.putNumber("Tracking State", tracking_state);
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
