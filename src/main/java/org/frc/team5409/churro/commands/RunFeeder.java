package org.frc.team5409.churro.commands;

import org.frc.team5409.churro.Robot;
import org.frc.team5409.churro.subsystems.FeederControl;

import edu.wpi.first.wpilibj2.command.CommandBase;

public final class RunFeeder extends CommandBase {
    private FeederControl m_feeder;

    public RunFeeder(FeederControl sys_feeder) {
        m_feeder = sys_feeder;

        addRequirements(m_feeder);
    }


    @Override
    public void initialize() {
        m_feeder.runAt(1);
    }

    @Override
    public void end(boolean interrupted) {
        m_feeder.stop();
    }
}