package org.usfirst.frc5409.Testrobot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc5409.Testrobot.Robot;
import org.usfirst.frc5409.Testrobot.ada.UNAlgo;
import org.usfirst.frc5409.Testrobot.limelight.lltype;
import org.usfirst.frc5409.Testrobot.util.Vector2;

public class Blink extends Command {
    public static long c_blink_period_ms = 1000;
    public UNAlgo algo;


    public Blink() {
        super("Blink");
        algo = new UNAlgo(0.015, 0.58, 0.175);
        requires(Robot.limelight);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        double[] dd = algo.compute(
            new Vector2(-355, 207),
            new Vector2(0.9044, -0.4267),
            new Vector2(-991, -263),
            new Vector2(0.2603, 0.9655)
        );
        Robot.limelight.setLedMode(lltype.LedMode.LED_ON);
        sleep();
        Robot.limelight.setLedMode(lltype.LedMode.LED_OFF);
        sleep();
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

    private void sleep() {
        try {
            Thread.sleep(c_blink_period_ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
