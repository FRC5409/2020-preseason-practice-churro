package org.frc.team5409.churro.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;

public final class TurretControl extends SubsystemBase {
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
        mo_pwm67_turret_velocity.setInverted(true);
        
        // addChild(mo_pwm7_turret_flywheel);

        en_qd45_turret_velocity = new Encoder(4, 5);
            en_qd45_turret_velocity.setDistancePerPulse(1.0d/40.0d);
            //en_qd45_turret_velocity.setReverseDirection(true);
        
        // addChild(en_qd45_turret_flywheel);

        pid_turret_rotation = new PIDController(0, 0, 0);
        pid_turret_velocity = new PIDController(0, 0, 0);
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

    public void setVelocity(double target) {
        synchronized(pid_turret_velocity) {
            pid_turret_velocity.setSetpoint(clamp(0, target, 90));
        };
    }

    public double getVelocity() {
        return en_qd45_turret_velocity.getRate();
    }
    
    public double getRotation() {
        return en_qd89_turret_rotation.getDistance();
    }

    public void setP(double P, boolean is_rotation) {
        PIDController controller = (is_rotation) ? pid_turret_rotation : pid_turret_velocity; 
        synchronized(controller) {
            controller.setP(P);
        }
    }

    public void setI(double I, boolean is_rotation) {
        PIDController controller = (is_rotation) ? pid_turret_rotation : pid_turret_velocity;
        synchronized(controller) {
            controller.setI(I);
        }
    }

    public void setD(double D, boolean is_rotation) {
        PIDController controller = (is_rotation) ? pid_turret_rotation : pid_turret_velocity;
        synchronized(controller) {
            controller.setD(D);
        }
    }

    @Override
    public void periodic() {
        double r_speed, v_speed;
        synchronized(pid_turret_rotation) {
            r_speed = pid_turret_rotation.calculate(getRotation());
        }

        synchronized(pid_turret_velocity) {
            v_speed = pid_turret_velocity.calculate(getVelocity());
        }

        SmartDashboard.putNumber("Turret Rotation Speed", r_speed);
        SmartDashboard.putNumber("Turret Velocity Speed", v_speed);
        SmartDashboard.putNumber("Turret dist", en_qd45_turret_velocity.getDistance());
        double max = SmartDashboard.getNumber("Max Rotation Speed", 0);
        double vv_max = SmartDashboard.getNumber("Max Velocity", 0);
        mo_pwm4_turret_rotation.set(clamp(-max, r_speed, max));
        mo_pwm67_turret_velocity.set(clamp(-vv_max, pid_turret_velocity.getSetpoint(), vv_max));
    }

    private double clamp(double mn, double v, double mx) {
        if (v > mx)
            return mx;
        else if (v < mn)
            return mn;
        return v;
    }

}