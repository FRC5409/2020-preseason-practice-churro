package org.frc.team5409.churro;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.TimedRobot;

import org.frc.team5409.churro.subsystems.*;
import org.frc.team5409.churro.commands.*;
import org.frc.team5409.churro.control.ServiceManager;



public class Robot extends TimedRobot {
    private static RobotContainer m_container = new RobotContainer();

    public static double     c_defaultHertz = 200;//Hz

    public static OI         oi;

    public Robot() {
        m_period = 1.0d / c_defaultHertz;

        //turretControl = new TurretControl();
        //feederControl = new FeederControl();
        //navX = new NavX();
    }

    public static RobotContainer getContainer() {
        return m_container;
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
        new AlignTurret().schedule();
    }

    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();
    }
}
