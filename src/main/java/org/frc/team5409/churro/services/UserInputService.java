package org.frc.team5409.churro.services;

import org.frc.team5409.churro.control.AbstractService;

import org.frc.team5409.churro.uinput.IController.Controller;
import org.frc.team5409.churro.uinput.IController;

public final class UserInputService extends AbstractService {
    private static final long m_update_period = 20; // 50 hz
    
    static {
        ServiceRegistry.register("UserInputService", 85719L);
    }

    private IController m_ctrl_main;
    private IController m_ctrl_secondary;

    public IController getController(Controller controller) {
        switch(controller) {
            case kMainDriver:      return m_ctrl_main;
            case kSecondaryDriver: return m_ctrl_secondary;
        }
        return null;
    }

    @Override
    protected void initialize() {
        m_ctrl_main = new IController(0);
        m_ctrl_secondary = new IController(1);
    }

    @Override
    protected void start() {
        ServiceRunner.runThread(this::run);
    }

    @Override
    protected void stop() {
        ServiceRunner.stopThread();
    }

    @Override
    protected void run() {
        m_ctrl_main.query();
        m_ctrl_secondary.query();

        ServiceRunner.sleep(m_update_period);
    }
}