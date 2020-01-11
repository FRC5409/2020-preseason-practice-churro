package org.frc.team5409.churro.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.frc.team5409.churro.commands.AlignTurret;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;

public final class TurretControl implements Subsystem {
    private Victor               mo_pwm4_turret_rotation;
    private Encoder              en_qd89_turret_rotation;

    private SpeedControllerGroup mo_pwm67_turret_velocity;
    private Encoder              en_qd45_turret_velocity;

    private PIDController        pid_turret_rotation;
    private PIDController        pid_turret_velocity;

    public TurretControl() {
        mo_pwm4_turret_rotation = new Victor(4);
        
        // addChild(mo_pwm4_turret_rotation);

        en_qd89_turret_rotation = new Encoder(8, 9);
            en_qd89_turret_rotation.setDistancePerPulse(1.0d/2.5d);
            en_qd89_turret_rotation.setReverseDirection(true);
        
        // addChild(en_qd89_turret_rotation);

        mo_pwm67_turret_velocity = new SpeedControllerGroup(
            new Spark(6),
            new Spark(7)
        );
        
        // addChild(mo_pwm7_turret_flywheel);

        //en_qd45_turret_velocity = new Encoder(4, 5);
        //    en_qd45_turret_velocity.setDistancePerPulse(1/4);
        
        // addChild(en_qd45_turret_flywheel);

        pid_turret_rotation = new PIDController(0, 0, 0);
        pid_turret_velocity = new PIDController(0, 0, 0);
        
        //setDefaultCommand(new AlignTurret());
    }

    public void zeroRotation() {
        en_qd89_turret_rotation.reset();
        synchronized(pid_turret_rotation) {
            pid_turret_rotation.reset();
        }
    }

    public synchronized void setRotation(double target) {
        synchronized(pid_turret_rotation) {
            pid_turret_rotation.setSetpoint(clamp(0, target, 90));
        }
    }

    public double getRotation() {
        return en_qd89_turret_rotation.getDistance();
    }

    public void setP(double P) {
        synchronized(pid_turret_rotation) {
            pid_turret_rotation.setP(P);
        }
    }

    public void setI(double I) {
        synchronized(pid_turret_rotation) {
            pid_turret_rotation.setI(I);
        }
    }

    public void setD(double D) {
        synchronized(pid_turret_rotation) {
            pid_turret_rotation.setD(D);
        }
    }

    @Override
    public void periodic() {
        double speed;
        synchronized(pid_turret_rotation) {
            speed = pid_turret_rotation.calculate(getRotation());
        }

        SmartDashboard.putNumber("Turret Rotation Speed", speed);
        double max = SmartDashboard.getNumber("Max Rotation Speed", 0);
        mo_pwm4_turret_rotation.set(clamp(-max, speed, max));
    }

    private double clamp(double mn, double v, double mx) {
        if (v > mx)
            return mx;
        else if (v < mn)
            return mn;
        return v;
    }

}