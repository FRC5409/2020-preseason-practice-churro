package org.frc.team5409.churro.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import org.frc.team5409.churro.util.JoystickType;
import org.frc.team5409.churro.util.Vec3;

import org.frc.team5409.churro.services.VisionService;
import org.frc.team5409.churro.control.ServiceManager;

import org.frc.team5409.churro.subsystems.FeederControl;
import org.frc.team5409.churro.subsystems.TurretControl;

import org.frc.team5409.churro.Robot;

public final class AlignTurret extends CommandBase {
    private VisionService m_vision;
    private TurretControl m_turret;
    private FeederControl m_feeder;

    private boolean       m_feeder_on;

    public AlignTurret() {
        m_vision = ServiceManager.getService("VisionService");

        m_turret = Robot.getSubsystem("TurretControl");
        m_feeder = Robot.getSubsystem("FeederControl");

        m_feeder_on = false;

        addRequirements(m_turret, m_feeder);
    }

    @Override
    public void initialize() {
        SmartDashboard.setDefaultNumber("Target height", 0);
        SmartDashboard.setDefaultNumber("Robot height", 0);
        SmartDashboard.setDefaultNumber("Turret P", 0);
        SmartDashboard.setDefaultNumber("Turret I", 0);
        SmartDashboard.setDefaultNumber("Turret D", 0);
        SmartDashboard.setDefaultNumber("Camera elevation", 0);
        SmartDashboard.setDefaultNumber("Zero angle", 90);
        SmartDashboard.setDefaultNumber("Target rotation", 0);
        SmartDashboard.setDefaultNumber("Max Rotation Speed", 0);

        m_turret.zeroRotation();
        m_feeder.stop();
    }

    @Override
    public void execute() {
        if (m_vision.hasTarget()) {
            Vec3 tpos = m_vision.getTarget();

            final double a0 = (SmartDashboard.getNumber("Camera elevation", 0) / 180) * Math.PI;

            final double tx = (tpos.x) / 180 * Math.PI;
            final double ty = tpos.y / 180 * Math.PI;

            final double hd = Math
                    .abs(SmartDashboard.getNumber("Target height", 0) - SmartDashboard.getNumber("Robot height", 0));
            final double dt = hd / (Math.sin(ty + a0) * 12);

            Vec3 rpos = new Vec3(dt * (Math.cos(ty) * Math.cos(tx)), dt * (Math.cos(ty) * (-Math.sin(tx))),
                    dt * Math.sin(ty));

            SmartDashboard.putString("Target 2D", String.format("{%f, %f}", tpos.x, tpos.y));
            SmartDashboard.putString(" Robot 3D", String.format("{%f, %f, %f}", rpos.x, rpos.y, rpos.z));
            
            m_turret.setRotation(m_turret.getRotation() + tpos.x);
        } // else
          // Robot.turretControl.m_pwm4_turret_rotation.set(0);

        m_turret.setP(SmartDashboard.getNumber("Turret P", 0));
        m_turret.setI(SmartDashboard.getNumber("Turret I", 0));
        m_turret.setD(SmartDashboard.getNumber("Turret D", 0));
        // m_subsystem.setRotation(SmartDashboard.getNumber("Target rotation", 0));
        SmartDashboard.putNumber("Rotation", m_turret.getRotation());

        if (Robot.oi.getJoystick(JoystickType.MAIN).getAButtonPressed()) {
            m_feeder_on = !m_feeder_on;
            
            if (m_feeder_on)
                m_feeder.runAt(0.1);
            else
                m_feeder.stop();
        }
    }

    @Override
    public boolean isFinished() {

        return false;
    }

    private void setRotation(double target) {
        final double speed = (target - m_turret.getRotation()) * SmartDashboard.getNumber("Turret P", 0);

        SmartDashboard.putNumber("Turret Speed", speed);

        // m_subsystem.m_pwm4_turret_rotation.set(speed);
    }
}