package org.frc.team5409.churro.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.frc.team5409.churro.commands.AlignTurret;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Victor;

public final class TurretControl extends Subsystem {
    public Victor  m_pwm4_turret_rotation;
    public Encoder e_qd89_turret_rotation;

    public Spark   m_pwm6_turret_flywheel;
    public Spark   m_pwm7_turret_flywheel;
    public Encoder e_qd12_turret_flywheel;

    public TurretControl() {
        m_pwm4_turret_rotation = new Victor(4);
        
        addChild(m_pwm4_turret_rotation);

        e_qd89_turret_rotation = new Encoder(8, 9);
            e_qd89_turret_rotation.setDistancePerPulse(1/4);
        
        addChild(e_qd89_turret_rotation);

        m_pwm6_turret_flywheel = new Spark(6);
        
        addChild(m_pwm6_turret_flywheel);

        m_pwm7_turret_flywheel = new Spark(7);
        
        addChild(m_pwm7_turret_flywheel);

        e_qd12_turret_flywheel = new Encoder(1, 2);
            e_qd12_turret_flywheel.setDistancePerPulse(1/4);
        
        addChild(e_qd12_turret_flywheel);
    }

    public void zeroRotation() {
        e_qd89_turret_rotation.reset();
    }

    public double getRotation() {
        return e_qd89_turret_rotation.getDistance();
    }


    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
        setDefaultCommand(new AlignTurret());
    }

}