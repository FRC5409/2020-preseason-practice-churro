package org.usfirst.frc5409.Testrobot.vision.control;

public class PipeSwitch {
    protected boolean m_switch;

    public PipeSwitch() {
        m_switch = false;
    }

    public PipeSwitch(boolean switch_value) {
        m_switch = switch_value;
    }

    public void set(boolean switch_value) {
        synchronized(this) {
            m_switch = switch_value;
        }
    }

    public void flip() {
        synchronized(this) {
            m_switch = !m_switch;
        }
    }

    public boolean get() {
        synchronized(this) {
            return m_switch;
        }
    }
}