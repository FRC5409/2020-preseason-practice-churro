package org.frc.team5409.churro.control;

import java.util.LinkedList;

public final class EventEmitter {
    private LinkedList<EventHandle> m_emissions;

    public EventEmitter() {
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
            for (var handle : m_emissions) {
                handle.getTarget().fire(stack);
            }
        }
    }
}