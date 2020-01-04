package org.frc.team5409.churro.control;

import org.frc.team5409.churro.control.exception.InvalidHandleException;

public final class EventHandle {
    private static  long          m_last_id = Long.MIN_VALUE;

    private final   long          m_id;
    private final   RunnableEvent m_target;

    private final   EventProxy    m_proxy;
    private         EventEmitter  m_emitter;

    private static synchronized long obtainId() {
        return m_last_id++;
    }

    public EventHandle(RunnableEvent target, EventProxy proxy) {
        if (target == null || proxy == null)
            throw new InvalidHandleException("Attempted to construct handle with null parameter.");

        m_id = obtainId();

        m_target = target;
        m_proxy = proxy;
        m_emitter = null;
    }
    
    public void bind(EventEmitter emitter) {
        if (m_emitter != null)
            throw new InvalidHandleException("Attempted to re-bind handle with established emitter.");

        synchronized(this) {
            m_emitter = emitter;
            m_emitter.bind(this);
        }
    }

    public void unbind() {
        if (m_emitter == null)
            throw new InvalidHandleException("Attempted to unbind handle with no emitter.");

        synchronized(this) {
            m_emitter.unbind(this);
            m_emitter = null;
        }
    }
    
    protected EventProxy getProxy() {
        return m_proxy;
    }

    protected RunnableEvent getTarget() {
        return m_target;
    }


    @Override
    public boolean equals(Object obj) {
        return ((EventHandle) obj).m_id == this.m_id;
    }
}