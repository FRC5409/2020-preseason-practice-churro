package org.frc.team5409.churro.control;

import java.util.LinkedList;

public final class EventEmitter {
    private static long                    m_last_id = Long.MIN_VALUE;

    private        LinkedList<EventHandle> m_emissions;
    private final  long                    m_id;

    private static synchronized long obtainId() {
        return m_last_id++;
    }

    public EventEmitter() {
        m_id = obtainId();
        m_emissions = new LinkedList<>();
    }

    protected void bind(EventHandle handle) {
        synchronized(m_emissions) {
            m_emissions.add(handle);
        }
    }

    protected void unbind(EventHandle handle) {
        synchronized(m_emissions) {
            m_emissions.remove(handle);
        }
    }

    public synchronized void emit(Object... args) {
        final EventStack stack = new EventStack(args);
        synchronized(m_emissions) {
            for (EventHandle handle : m_emissions) {
                handle.getProxy().push(new SendableEvent(handle, stack));
            }
        }
    }
}