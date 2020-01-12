package org.frc.team5409.churro.control;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.frc.team5409.churro.control.exception.ControlException;

public final class EventProxy {
    private BlockingQueue<SendableEvent> m_queue;
    private Thread                       m_worker;

    public EventProxy() {
        m_queue = new LinkedBlockingQueue<SendableEvent>();
        m_worker = new Thread(this::work);

        m_worker.start();
    }

    protected void push(SendableEvent event) {
        m_queue.add(event);
    }

    private void work() {
        while (!Thread.interrupted()) try {
            SendableEvent event = m_queue.take();     
            try {
                event.handle.getTarget().fire(event.stack);
            } catch (Exception e) {
                event.handle.unbind();
                throw new ControlException("Unexpected exception thrown during event execution.", e);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ControlException e) {
            e.printStackTrace();
        }
    }
}