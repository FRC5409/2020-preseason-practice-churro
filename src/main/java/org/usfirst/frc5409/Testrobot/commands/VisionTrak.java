package org.usfirst.frc5409.Testrobot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc5409.Testrobot.Robot;
import org.usfirst.frc5409.Testrobot.limelight.LedMode;
import org.usfirst.frc5409.Testrobot.util.JoystickType;

public class VisionTrak extends Command {
    private boolean m_debounce;


    public VisionTrak() {
        super("VisionTrak");

        m_debounce = false;

        requires(Robot.drivetrain);
        requires(Robot.limelight);
        requires(Robot.navX);
    }

    @Override
    protected void initialize() {
        Robot.limelight.setLedMode(LedMode.LED_ON);
        Robot.drivetrain.reset();
        Robot.navX.zeroYaw();
    }

    @Override
    protected void execute() {
        
    }

    @Override
    protected boolean isFinished() {
        return Robot.oi.getJoystick(JoystickType.MAIN).getAButton();
    }

    @Override
    protected void end() {
        Robot.limelight.setLedMode(LedMode.LED_OFF);
        Robot.drivetrain.reset();
        Robot.navX.zeroYaw();
    }

    @Override
    protected void interrupted() {
        Robot.limelight.setLedMode(LedMode.LED_OFF);
        Robot.drivetrain.reset();
        Robot.navX.zeroYaw();
    }
}
