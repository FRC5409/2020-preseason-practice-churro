package org.frc.team5409.churro;

import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj.TimedRobot;

import org.frc.team5409.churro.control.ServiceManager;

public class Robot extends TimedRobot {
    private static RobotContainer m_container;

    public Robot() {
        m_container = new RobotContainer();
            m_container.initialize();

        m_period = 1.0d / 200.0d;
    }

	public static <T extends Subsystem> T getSubsystem(String name) {
		return m_container.getSubsystem(name);
    }
    
    public static <T extends Command> T getCommand(String name) {
		return m_container.getCommand(name);
	}

    @Override
    public void robotInit() {
        m_container = new RobotContainer();

        oi = new OI();

        ServiceManager.getInstance().startServices();
    }

    @Override
    public void disabledInit(){
        ServiceManager.getInstance().stopServices();
    }

    @Override
    public void disabledPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        
    }

    @Override
    public void autonomousPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        getCommand("AlignTurret").schedule();
    }

    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();
    }
}
