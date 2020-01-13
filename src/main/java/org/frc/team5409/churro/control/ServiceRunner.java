package org.frc.team5409.churro.control;

import java.util.concurrent.atomic.AtomicBoolean;

import org.frc.team5409.churro.control.exception.ControlException;

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
            m_interrupt.set(true);
            m_thread.interrupt();

            if (Thread.currentThread().getId() != m_thread.getId()) {
                try {
                    m_thread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void interrupt() {
        if (this.isAlive()) {
            m_interrupt.set(true);
        }
    }

    public boolean isAlive() {
        return m_thread.isAlive();
    }

    public boolean interrupted() {
        return m_interrupt.get();
    }

    private Runnable wrap(Runnable target)  {
        return () -> {
            try {
                while (!this.interrupted())
                    target.run();
                m_interrupt.set(false);
            } catch (Exception e) {
                if (e instanceof InterruptedException) {
                    m_interrupt.set(false);
                    Thread.currentThread().interrupt();
                } else
                    new ControlException("Service encountered error during execution.", e).printStackTrace();
            }
        };
    }
}