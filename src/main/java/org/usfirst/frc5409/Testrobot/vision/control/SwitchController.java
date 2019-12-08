package org.usfirst.frc5409.Testrobot.vision.control;

public class SwitchController {
    protected boolean m_switch;
    protected Object  m_lock_mutex;

    SwitchController() {
        m_switch = false;
    }

    SwitchController(boolean switch_value) {
        m_switch = switch_value;
    }

    public void set(boolean switch_value) {
        synchronized(m_lock_mutex) {
            m_switch = switch_value;
        }
    }

    public boolean get() {
        synchronized(m_lock_mutex) {
            return m_switch;
        }
    }
}