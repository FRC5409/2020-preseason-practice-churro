package org.frc.team5409.churro.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import org.frc.team5409.churro.Robot;

import org.frc.team5409.churro.subsystems.TurretControl;
import org.frc.team5409.churro.subsystems.Limelight;

import org.frc.team5409.churro.limelight.LedMode;

public final class AlignTurret extends CommandBase {
    private TurretControl m_turret;
    private Limelight     m_limelight;

    private long          m_last_sweep;

    public AlignTurret() 
        m_turret = Robot.getSubsystem("TurretControl");
        m_limelight = Robot.getSubsystem("Limelight");

        addRequirements(m_turret, m_limelight);
    }

    @Override
    public void initialize() {
        m_limelight.setLedMode(LedMode.LED_ON);
    }

    @Override
    public void execute() {
        m_turret.setVelocity(SmartDashboard.getNumber("Target Velocity", 0));

        if (m_trigger.isToggled()) {
            m_limelight.setLedMode(LedMode.LED_ON);

            if (m_limelight.hasTarget() && m_trigger.isToggled()) {
                Vec2 tpos = m_limelight.getTarget();
                
                m_limelight.setLedMode(LedMode.LED_ON);

                final double a0 = (SmartDashboard.getNumber("Camera elevation", 0) / 180) * Math.PI;

                final double tx = (tpos.x) / 180 * Math.PI;
                final double ty = tpos.y / 180 * Math.PI;

                final double hd = Math.abs(SmartDashboard.getNumber("Target height", 0) - SmartDashboard.getNumber("Robot height", 0));
                final double dt = hd / (Math.sin(ty + a0));

                Vec3 rpos = new Vec3(dt * (Math.cos(ty) * Math.cos(tx)), dt * (Math.cos(ty) * (-Math.sin(tx))),
                        dt * Math.sin(ty));

                SmartDashboard.putString("Target 2D", String.format("{%f, %f}", tpos.x, tpos.y));
                SmartDashboard.putString(" Robot 3D", String.format("{%f, %f, %f}", rpos.x, rpos.y, rpos.z));
                
                m_turret.setRotation(m_turret.getRotation() + tpos.x);
            } else
                m_turret.setRotation(0);
        } else {
            m_limelight.setLedMode(LedMode.LED_OFF);
            m_turret.setRotation(0);
        }

        m_turret.setP(SmartDashboard.getNumber("Turret P", 0), true);
        m_turret.setI(SmartDashboard.getNumber("Turret I", 0), true);
        m_turret.setD(SmartDashboard.getNumber("Turret D", 0), true);
        
        m_turret.setP(SmartDashboard.getNumber("Velocity P", 0), false);
        m_turret.setI(SmartDashboard.getNumber("Velocity I", 0), false);
        //m_turret.setD(SmartDashboard.getNumber("Velocity D", 0), false);

        /*if (m_triggr2.isToggled()) {
            m_turret.setVelocity(1);
        } else
            m_turret.setVelocity(0);*/

        // m_subsystem.setRotation(SmartDashboard.getNumber("Target rotation", 0));
        SmartDashboard.putNumber("Rotation", m_turret.getRotation());
        SmartDashboard.putNumber("Velocity", m_turret.getVelocity());
    }
}