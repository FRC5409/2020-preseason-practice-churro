package org.frc.team5409.churro.control;

import java.util.concurrent.atomic.AtomicBoolean;

public final class ServiceRunner {
    private  final String        m_name;

    private        AtomicBoolean m_interrupt;
    private        Thread        m_thread;

    protected ServiceRunner(long uid) {
        m_name = "ServiceThread["+uid+"]";

        m_thread = new Thread(m_name);
        m_interrupt = new AtomicBoolean(false);
    }

    public void run(Runnable target) {
        if (!m_thread.isAlive()) {
            m_thread = new Thread(wrap(target), m_name);
            m_interrupt.set(false);
            m_thread.start();
        }
    }

    public boolean isAlive() {
        return m_thread.isAlive();
    }

    public void interrupt() {
        if (m_thread.isAlive())
            m_interrupt.set(true);
    }

    public boolean interrupted() {
        return m_interrupt.get();
    }

    private Runnable wrap(Runnable target)  {
        return () -> { target.run(); m_interrupt.set(false); };
    }
}