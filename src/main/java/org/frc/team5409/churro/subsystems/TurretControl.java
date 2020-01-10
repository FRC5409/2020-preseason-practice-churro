package org.frc.team5409.churro.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.frc.team5409.churro.commands.AlignTurret;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Victor;

public final class TurretControl extends Subsystem {
    private Victor  mo_pwm4_turret_rotation;
    private Encoder en_qd89_turret_rotation;

    private Spark   mo_pwm6_turret_flywheel;
    private Spark   mo_pwm7_turret_flywheel;
    private Encoder en_qd45_turret_flywheel;

    private PIDController m_rot_controller;

    public TurretControl() {
        mo_pwm4_turret_rotation = new Victor(4);
        
        addChild(mo_pwm4_turret_rotation);

        en_qd89_turret_rotation = new Encoder(8, 9);
            en_qd89_turret_rotation.setDistancePerPulse(1.0d/2.5d);
            en_qd89_turret_rotation.setReverseDirection(true);
        
        addChild(en_qd89_turret_rotation);

        mo_pwm6_turret_flywheel = new Spark(6);
        
        addChild(mo_pwm6_turret_flywheel);

        mo_pwm7_turret_flywheel = new Spark(7);
        
        addChild(mo_pwm7_turret_flywheel);

        en_qd45_turret_flywheel = new Encoder(4, 5);
            en_qd45_turret_flywheel.setDistancePerPulse(1/4);
        
        addChild(en_qd45_turret_flywheel);

        m_rot_controller = new PIDController(0, 0, 0);
    }

    public void zeroRotation() {
        en_qd89_turret_rotation.reset();
        synchronized(m_rot_controller) {
            m_rot_controller.reset();
        }
    }

    public synchronized void setRotation(double target) {
        synchronized(m_rot_controller) {
            m_rot_controller.setSetpoint(clamp(0, target, 90));
        }
    }

    public double getRotation() {
        return en_qd89_turret_rotation.getDistance();
    }

    public void setP(double P) {
        synchronized(m_rot_controller) {
            m_rot_controller.setP(P);
        }
    }

    public void setI(double I) {
        synchronized(m_rot_controller) {
            m_rot_controller.setI(I);
        }
    }

    public void setD(double D) {
        synchronized(m_rot_controller) {
            m_rot_controller.setD(D);
        }
    }

    @Override
    public void periodic() {
        double speed;
        synchronized(m_rot_controller) {
            speed = m_rot_controller.calculate(getRotation() );
        }

        SmartDashboard.putNumber("Turret Rotation Speed", speed);
        double max = SmartDashboard.getNumber("Max Rotation Speed", 0);
        mo_pwm4_turret_rotation.set(clamp(-max, speed, max));
    }

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
        setDefaultCommand(new AlignTurret()); 
    }
    
    private double clamp(double mn, double v, double mx) {
        if (v > mx)
            return mx;
        else if (v < mn)
            return mn;
        return v;
    }

}