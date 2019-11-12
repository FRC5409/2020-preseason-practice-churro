package org.usfirst.frc5409.Testrobot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc5409.Testrobot.Robot;
import org.usfirst.frc5409.Testrobot.limelight.lltype;

public class Blink extends Command {

    public Blink() {
        super("Blink");

        requires(Robot.limelight);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.limelight.setLedMode(lltype.LedMode.LED_ON);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Robot.limelight.setLedMode(lltype.LedMode.LED_OFF);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
 
    }

    @Override
    protected void interrupted() {

    }
}
