package org.frc.team5409.churro.commands;

import org.frc.team5409.churro.Robot;
import org.frc.team5409.churro.control.ServiceManager;
import org.frc.team5409.churro.services.VisionService;
import org.frc.team5409.churro.subsystems.TurretControl;
import org.frc.team5409.churro.util.Vec3;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class AlignTurret extends Command {
    private VisionService m_service;
    private TurretControl m_subsystem;

    public AlignTurret() {
        requires(Robot.turretControl);
        m_subsystem = Robot.turretControl;
    }

    @Override
    protected void initialize() {
        m_service = ServiceManager.get("VisionService");

        SmartDashboard.setDefaultNumber("Target height", 0);
        SmartDashboard.setDefaultNumber("Robot height", 0);
        SmartDashboard.setDefaultNumber("Turret P", 0);
        SmartDashboard.setDefaultNumber("Turret I", 0);
        SmartDashboard.setDefaultNumber("Turret D", 0);
        SmartDashboard.setDefaultNumber("Camera elevation", 0);
        SmartDashboard.setDefaultNumber("Zero angle", 90);
        SmartDashboard.setDefaultNumber("Target rotation", 0);
        SmartDashboard.setDefaultNumber("Max Rotation Speed", 0);

        m_subsystem.zeroRotation();
    }

    @Override
    protected void execute() {
        if (m_service.hasTarget()) {
            Vec3 tpos = m_service.getTarget();
            
            final double a0 = (SmartDashboard.getNumber("Camera elevation", 0)/180) * Math.PI;

            final double tx = (tpos.x)/180 * Math.PI;
            final double ty = tpos.y/180 * Math.PI;

            final double hd = Math.abs(SmartDashboard.getNumber("Target height", 0) - SmartDashboard.getNumber("Robot height", 0));
            final double dt = hd / (Math.sin(ty+a0)*12);

            Vec3 rpos = new Vec3(
                dt * (Math.cos(ty) *   Math.cos(tx)),
                dt * (Math.cos(ty) * (-Math.sin(tx))),
                dt *  Math.sin(ty)
            );

            SmartDashboard.putString("Target 2D", String.format("{%f, %f}", tpos.x, tpos.y));
            SmartDashboard.putString(" Robot 3D", String.format("{%f, %f, %f}", rpos.x, rpos.y, rpos.z));
            m_subsystem.setRotation(m_subsystem.getRotation()+tpos.x);
        } //else
            //Robot.turretControl.m_pwm4_turret_rotation.set(0);

        m_subsystem.setP(SmartDashboard.getNumber("Turret P", 0));
        m_subsystem.setI(SmartDashboard.getNumber("Turret I", 0));
        m_subsystem.setD(SmartDashboard.getNumber("Turret D", 0));
        //m_subsystem.setRotation(SmartDashboard.getNumber("Target rotation", 0));
        SmartDashboard.putNumber("Rotation", m_subsystem.getRotation());
    }

    @Override
    protected boolean isFinished() {

        return false;
    }

    @Override
    protected void end() {
        //Robot.turretControl.m_pwm4_turret_rotation.set(0);
        super.end();
    }

    private void setRotation(double target) {
        final double speed = (target-m_subsystem.getRotation()) * SmartDashboard.getNumber("Turret P", 0);

        SmartDashboard.putNumber("Turret Speed", speed);

        //m_subsystem.m_pwm4_turret_rotation.set(speed);
    }
}