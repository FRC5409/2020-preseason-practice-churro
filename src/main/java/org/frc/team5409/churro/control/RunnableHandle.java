package org.frc.team5409.churro.control;

import org.frc.team5409.churro.control.exception.InvalidHandleException;

public final class RunnableHandle extends EventHandle {
    private final   RunnableStackedEvent m_target;
    private         EventEmitter         m_emitter;

    public RunnableHandle(RunnableEvent target) {
        this(stack -> {
            target.fire();
        });
    }

    public RunnableHandle(RunnableStackedEvent target) {
        super();

        if (target == null)
            throw new InvalidHandleException("Attempted to construct handle with null parameter.");

        m_target = target;
        m_emitter = null;
    }
    
    @Override
    public void bind(EventEmitter emitter) {
        if (m_emitter != null)
            throw new InvalidHandleException("Attempted to re-bind handle with established emitter.");

        synchronized(this) {
            m_emitter = emitter;
            m_emitter.bind(this);
        }
    }

    @Override
    public void unbind() {
        if (m_emitter == null)
            throw new InvalidHandleException("Attempted to unbind handle with no emitter.");

        synchronized(this) {
            m_emitter.unbind(this);
            m_emitter = null;
        }
    }

    protected RunnableStackedEvent getTarget() {
        synchronized(this) {
            return m_target;
        }
    }

    protected EventEmitter getEmitter() {
        synchronized(this) {
            return m_emitter;
        }
    }
}