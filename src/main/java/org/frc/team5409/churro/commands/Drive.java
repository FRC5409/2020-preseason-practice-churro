package org.frc.team5409.churro.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc.team5409.churro.control.ServiceManager;
import org.frc.team5409.churro.services.UserInputService;
import org.frc.team5409.churro.subsystems.Drivetrain;
import org.frc.team5409.churro.uinput.*;
import org.frc.team5409.churro.uinput.IController.*;

public class Drive extends CommandBase {
    private Drivetrain m_drive;

    private ITrigger   m_ui_axis_fwd_vel;
    private ITrigger   m_ui_axis_bwd_vel;
    private IJoystick  m_ui_stck_rot;

    public Drive(Drivetrain sys_drive) {
        m_drive = sys_drive;

        IController controller = ServiceManager.getService("UserInputService", UserInputService.class).getController(Controller.kMainDriver);
            m_ui_axis_fwd_vel = controller.getTrigger(Hand.kRight);
            m_ui_axis_bwd_vel = controller.getTrigger(Hand.kLeft);
            m_ui_stck_rot = controller.getJoystick(Hand.kLeft);

        addRequirements(m_drive);
    }

    @Override
    public void initialize() {
        m_drive.reset();
        m_drive.resetTrack();
    }

    @Override
    public void execute() {
        double axis_v = m_ui_axis_bwd_vel.getValue() - m_ui_axis_fwd_vel.getValue();
        double axis_r = m_ui_stck_rot.getAxis().x;

       //  m_drive.tankDrive(-m_ui_axis_bwd_vel.getValue(), -m_ui_axis_fwd_vel.getValue());
        m_drive.arcadeDrive(axis_r, axis_v);
    }

    @Override
    public boolean isFinished() { 
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_drive.reset();
    }
}
