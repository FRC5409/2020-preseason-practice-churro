package org.frc.team5409.churro.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.Encoder;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Drivetrain implements Subsystem {
    private TalonSRX mo_C01_drive_left;
    private TalonSRX mo_C02_drive_left;
    private TalonSRX mo_C03_drive_right;
    private TalonSRX mo_CO4_drive_right;

    public Drivetrain() {
        mo_C01_drive_left = new TalonSRX(0);

        mo_C02_drive_left = new TalonSRX(1);
            mo_C02_drive_left.follow(mo_C01_drive_left);

        mo_C03_drive_right = new TalonSRX(2);

        mo_CO4_drive_right = new TalonSRX(4);
            mo_CO4_drive_right.follow(mo_C03_drive_right);
    }

    public void tankDrive(double left_output, double right_output) {
        setVelocity(clamp(left_output), clamp(right_output));
    }

    public void arcadeDrive(double speed_x, double rot_z) {
        double left_output, right_output;
        limit(speed_x);
        limit(rot_z);

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
        
        setVelocity(left_output, -right_output);
    }

    public void reset() {
        setVelocity(0, 0);
    }

    private synchronized void setVelocity(double left_output, double right_output) {
        mo_C01_drive_left.set(ControlMode.PercentOutput, left_output);
        mo_C03_drive_right.set(ControlMode.PercentOutput, -right_output);
    }

    private double limit(double value) {
        if (value > 1)
            return 1;
        else if (value < 0)
            return 0;
        return value;
    }

    private double clamp(double value) {
        if (value > 1)
            return 1;
        else if (value < -1)
            return -1;
        return value;
    }
}

