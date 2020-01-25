package org.frc.team5409.churro.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.frc.team5409.churro.drive.ForwardKinematicTrack;
import org.frc.team5409.churro.util.Range;
import org.frc.team5409.churro.util.Vec2;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Drivetrain extends SubsystemBase {
    private ForwardKinematicTrack m_fkt_drive;

    private TalonSRX              mo_C01_drive_left;
    private TalonSRX              mo_C02_drive_left;
    private TalonSRX              mo_C03_drive_right;
    private TalonSRX              mo_C04_drive_right;

    private Encoder               en_qd01_drive_left;
    private Encoder               en_qd23_drive_right;

    public Drivetrain() {
        m_fkt_drive = new ForwardKinematicTrack(2 /*TODO CONSTANT*/);

        mo_C01_drive_left = new TalonSRX(1);

        mo_C02_drive_left = new TalonSRX(3);
            mo_C02_drive_left.follow(mo_C01_drive_left);

        mo_C03_drive_right = new TalonSRX(2);
            mo_C03_drive_right.setInverted(true);

        mo_C04_drive_right = new TalonSRX(4);
            mo_C04_drive_right.follow(mo_C03_drive_right);
            mo_C04_drive_right.setInverted(true);
        
        en_qd01_drive_left = new Encoder(0, 1);

        en_qd23_drive_right = new Encoder(2, 3);
        
    }

    public void tankDrive(double left_output, double right_output) {
        internal_setVelocity(left_output, right_output);
    }

    public void arcadeDrive(double speed_x, double rot_z) {
        double left_output, right_output;

        speed_x = Range.clamp(-1, speed_x, 1);
        rot_z = Range.clamp(-1, rot_z, 1);

        //Might have to flip these for some reason
        speed_x = Math.copySign(speed_x * speed_x, speed_x);
        rot_z = Math.copySign(rot_z * rot_z, rot_z);

        double max_input = Math.copySign(Math.max(Math.abs(speed_x), Math.abs(rot_z)), speed_x);
        if (speed_x >= 0.0) {
            if (rot_z >= 0.0) {
                left_output = max_input;
                right_output = speed_x - rot_z;
            } else {
                left_output = speed_x + rot_z;
                right_output = max_input;
            }
        } else {
            if (rot_z >= 0.0) {
                left_output = speed_x + rot_z;
                right_output = max_input;
            } else {
                left_output = max_input;
                right_output = speed_x - rot_z;
            }
        }   
        
        internal_setVelocity(left_output, -right_output);
    }

    public void reset() {
        en_qd01_drive_left.reset();
        en_qd23_drive_right.reset();

        internal_setVelocity(0, 0);
    }

    public Vec2 getTrackPosition() {
        return m_fkt_drive.getPosition();
    }

    public Vec2 getTrackDirection() {
        return m_fkt_drive.getDirection();
    }

    public void adjustTrack(Vec2 position, Vec2 direction) {
        m_fkt_drive.setPosition(position);
        m_fkt_drive.setDirection(direction);
    }

    public void resetTrack() {
        m_fkt_drive.reset();
    }

    public double[] getVelocity() {
       return internal_getVelocity();
    }

    public boolean isMoving() {
        double[] velocities = getVelocity();
        return (Math.abs(velocities[0]) + Math.abs(velocities[1]) > 0.2 /*TODO CONSTANT*/);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Left distance",  en_qd01_drive_left.getDistance());
        SmartDashboard.putNumber("Right distance",  en_qd23_drive_right.getDistance());

        double[] velocities = getVelocity();
        m_fkt_drive.feed(velocities[0], velocities[1]);
    }

    private void internal_setVelocity(double left_output, double right_output) {
        mo_C01_drive_left.set(ControlMode.PercentOutput, Range.clamp(-1, left_output, 1));
        mo_C03_drive_right.set(ControlMode.PercentOutput, Range.clamp(-1, right_output, 1));
    }

    private double[] internal_getVelocity() {
        double[] velocities = new double[2];
            velocities[0] = en_qd01_drive_left.getRate();
            velocities[1] = en_qd23_drive_right.getRate();
        return velocities;
    }
}

