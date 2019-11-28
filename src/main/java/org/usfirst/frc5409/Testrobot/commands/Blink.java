package org.usfirst.frc5409.Testrobot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc5409.Testrobot.Robot;
import org.usfirst.frc5409.Testrobot.limelight.*;
import org.usfirst.frc5409.Testrobot.util.JoystickType;

public class Blink extends Command {
    private boolean m_led_on = false;

    public Blink() {
        super("Blink");
        requires(Robot.limelight);
    }

    @Override
    protected void initialize() {
        Robot.limelight.setLedMode(LedMode.LED_OFF);
    }

    @Override
    protected void execute() {
        boolean pressed = Robot.oi.getJoystick(JoystickType.MAIN).getAButtonPressed();

        m_led_on = !m_led_on;

        if (m_led_on)
            Robot.limelight.setLedMode(LedMode.LED_ON);
        else
            Robot.limelight.setLedMode(LedMode.LED_OFF);
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
