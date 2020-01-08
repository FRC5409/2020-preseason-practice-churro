package org.frc.team5409.churro.commands;

import org.frc.team5409.churro.Robot;
import org.frc.team5409.churro.control.ServiceManager;
import org.frc.team5409.churro.services.VisionService;
import org.frc.team5409.churro.util.Vec3;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class AlignTurret extends Command {
    private VisionService m_service;
    
    @Override
    protected void initialize() {
        m_service = ServiceManager.get(VisionService.class);
        requires(Robot.turretControl);

        SmartDashboard.setDefaultNumber("Target height", 0);
        SmartDashboard.setDefaultNumber("Robot height", 0);
        SmartDashboard.setDefaultNumber("Turret P", 0);
    }

    @Override
    protected void execute() {
        if (m_service.hasTarget()) {
            Vec3 tpos = m_service.getTarget();
            
            final double tx = tpos.x/180 * Math.PI;
            final double ty = tpos.y/180 * Math.PI;

            final double hd = Math.abs(SmartDashboard.getNumber("Target height", 0) - SmartDashboard.getNumber("Robot height", 0));
            final double dt = hd / (Math.sin(ty+ (10/180*Math.PI))*12);

            Vec3 rpos = new Vec3(
                dt * (Math.cos(ty) *   Math.cos(tx)),
                dt * (Math.cos(ty) * (-Math.sin(tx))),
                dt *  Math.sin(ty)
            );

            SmartDashboard.putString("Target 2D", String.format("{%f, %f, &f}", tpos.x, tpos.y));
            SmartDashboard.putString(" Robot 3D", String.format("{%f, %f, &f}", rpos.x, rpos.y, rpos.z));
        } else
            Robot.turretControl.m_pwm4_turret_rotation.set(0);
    }

    @Override
    protected boolean isFinished() {

        return false;
    }

    @Override
    protected void end() {
        Robot.turretControl.m_pwm4_turret_rotation.set(0);
        super.end();
    }

    private void setRotation(double target) {
        final double speed = (target-Robot.turretControl.getRotation()) * SmartDashboard.getNumber("Turret P", 0);

        SmartDashboard.putNumber("Turret Speed", speed);

        Robot.turretControl.m_pwm4_turret_rotation.set(speed);
    }
}