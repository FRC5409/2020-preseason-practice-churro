package org.frc.team5409.churro.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

import org.frc.team5409.churro.util.Range;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Spark;

public final class TurretControl extends SubsystemBase {
    public enum TurretMode {
        /** Configures the turret to run with full speed and functionality. */
        TURRET_FAST(0.8, 0.7),

        /** Configures the turret to run with half speed and no velocity. Ideal for sweeping the turret */
        TURRET_SWEEP(0.5, 0.0);

        public double getRotationSetting() {
            return m_rot_setting;
        }

        public double getVelocitySetting() {
            return m_vel_setting;
        }
        
        private final double m_rot_setting, m_vel_setting;

        private TurretMode(double rot_setting, double vel_setting) {
            m_rot_setting = rot_setting;
            m_vel_setting = vel_setting;
        }
    }

    private final Range          m_rotation_range;

    private Victor               mo_pwm4_turret_rotation;
    private Encoder              en_qd89_turret_rotation;

    private SpeedControllerGroup mo_pwm67_turret_velocity;
    private Encoder              en_qd45_turret_velocity;

    private PIDController        pid_turret_rotation;
    private PIDController        pid_turret_velocity;

    private TurretMode           m_turret_mode;

    public TurretControl() {
        m_rotation_range = new Range(-20, 90);

        mo_pwm4_turret_rotation = new Victor(4);
        
        en_qd89_turret_rotation = new Encoder(8, 9);
            en_qd89_turret_rotation.setDistancePerPulse(1.0d/2.5d);
            en_qd89_turret_rotation.setReverseDirection(true);

        pid_turret_rotation = new PIDController(0.1, 0.02, 0.01);

        mo_pwm67_turret_velocity = new SpeedControllerGroup(
            new Spark(6),
            new Spark(7)
        );
        mo_pwm67_turret_velocity.setInverted(true);

        en_qd45_turret_velocity = new Encoder(4, 5);
            en_qd45_turret_velocity.setDistancePerPulse(1.0d/(40.0d*12.0d)); //ft per sec
            en_qd45_turret_velocity.setReverseDirection(true);

        pid_turret_velocity = new PIDController(0.00005, 0.00006, 0);

        m_turret_mode = TurretMode.TURRET_FAST;
    }

    public void setMode(TurretMode mode) {
        m_turret_mode = mode;
    }

    public void setRotation(double target) {
        pid_turret_rotation.setSetpoint(m_rotation_range.clamp(target));
    }

    public void setVelocity(double target) {
        pid_turret_velocity.setSetpoint(target);
    }

    public double getVelocity() {
        return en_qd45_turret_velocity.getRate();
    }
    
    public double getRotation() {
        return en_qd89_turret_rotation.getDistance();
    }

    public void setP(double P, boolean is_rotation) {
        PIDController controller = (is_rotation) ? pid_turret_rotation : pid_turret_velocity;
        controller.setP(P);
    }

    public void setI(double I, boolean is_rotation) {
        PIDController controller = (is_rotation) ? pid_turret_rotation : pid_turret_velocity;
        controller.setI(I);
    }

    public void setD(double D, boolean is_rotation) {
        PIDController controller = (is_rotation) ? pid_turret_rotation : pid_turret_velocity;
        controller.setD(D);
    }

    @Override
    public void periodic() {
        mo_pwm4_turret_rotation.set(
            Range.clamp(
                -m_turret_mode.getRotationSetting(),
                pid_turret_rotation.calculate(getRotation()),
                m_turret_mode.getRotationSetting()
            )
        );

        mo_pwm67_turret_velocity.set(
            Range.clamp(
                -m_turret_mode.getRotationSetting(),
                mo_pwm67_turret_velocity.get() + pid_turret_velocity.calculate(getVelocity()),
                m_turret_mode.getRotationSetting()
            )
        );
    }

}