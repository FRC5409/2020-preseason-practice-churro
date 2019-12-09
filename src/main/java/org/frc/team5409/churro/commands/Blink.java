package org.frc.team5409.churro.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.frc.team5409.churro.Robot;
import org.frc.team5409.churro.limelight.*;
import org.frc.team5409.churro.navx.data.*;
import org.frc.team5409.churro.util.JoystickType;

public class Blink extends Command {
    private boolean m_led_on = false;

    public Blink() {
        super("Blink");
        requires(Robot.limelight);
        requires(Robot.navX);
    }

    @Override
    protected void initialize() {
        Robot.limelight.setLedMode(LedMode.LED_OFF);
    }

    @Override
    protected void execute() {
        boolean pressed = Robot.oi.getJoystick(JoystickType.MAIN).getAButtonPressed();

        if (pressed)
            m_led_on = !m_led_on;

        if (m_led_on)
            Robot.limelight.setLedMode(LedMode.LED_ON);
        else
            Robot.limelight.setLedMode(LedMode.LED_OFF);
        
        YPRHData yprh = Robot.navX.getYPRH();
            SmartDashboard.putNumber("Yaw", yprh.yaw);
            SmartDashboard.putNumber("Pitch", yprh.ptch);
            SmartDashboard.putNumber("Roll", yprh.roll);
            SmartDashboard.putNumber("Heading", yprh.head);
            SmartDashboard.putNumber("Fused Heading", yprh.fhead);

        IVXYZData ivxyz = Robot.navX.getIVXYZ();
            SmartDashboard.putNumber("Velocity X", ivxyz.vx);
            SmartDashboard.putNumber("Velocity Y", ivxyz.vy);
            SmartDashboard.putNumber("Velocity", Math.sqrt(ivxyz.vx*ivxyz.vx + ivxyz.vy*ivxyz.vy));
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.limelight.setLedMode(LedMode.LED_OFF);
    }

    @Override
    protected void interrupted() {

    }
}
