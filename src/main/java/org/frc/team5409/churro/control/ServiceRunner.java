package org.frc.team5409.churro.control;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Service Runner utility.
 * 
 * <p> Used for controlling service related 
 * threads. </p>
 * 
 * @author Keith Davies
 * @see AbstractService
 */
public final class ServiceRunner {
    private  final String        m_name;

    private        AtomicBoolean m_interrupt;
    private        Thread        m_thread;

    protected ServiceRunner(long uid) {
        m_name = "ServiceThread["+uid+"]";
        m_thread = new Thread(m_name);
        m_interrupt = new AtomicBoolean(false);
    }

    public void runThread(Runnable target) {
        if (!this.isAlive()) {
            m_thread = new Thread(wrap(target), m_name);
            m_interrupt.set(false);
            m_thread.start();
        }
    }

    public void stopThread() {
        if (this.isAlive()) {
            m_thread.interrupt();
            m_interrupt.set(true);
            join();
        }
    }

    public void interrupt() {
        if (this.isAlive()) {
            m_thread.interrupt();
            m_interrupt.set(true);
        }
    }

    public boolean isAlive() {
        return m_thread.isAlive();
    }

    public boolean interrupted() {
        return m_interrupt.get();
    }

    protected void join() {
        if (this.isAlive() && Thread.currentThread().getId() != m_thread.getId()) {
            try {
                m_thread.join();
            } catch (Exception e) {
                // Should never throw
            }
        }
    }

    private Runnable wrap(Runnable target)  {
        return () -> {
            while (!this.interrupted())
                target.run();
            m_interrupt.set(false);
        };
    }
}