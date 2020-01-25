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

    @Override
    public void robotInit() {
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
        ServiceManager.getInstance().startServices();

        //getCommand("AlignTurret").schedule();
        getCommand("Drive").schedule();
    }

    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();
    }
}
