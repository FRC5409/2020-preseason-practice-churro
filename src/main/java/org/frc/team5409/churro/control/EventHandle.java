package org.frc.team5409.churro.control;

import edu.wpi.first.wpilibj2.command.Command;

import org.frc.team5409.churro.control.exception.InvalidHandleException;

public final class EventHandle {
    private static  long                 m_last_id = Long.MIN_VALUE;

    private final   long                 m_id;
    private final   RunnableStackedEvent m_target;

    private         EventEmitter         m_emitter;

    private static synchronized long obtainId() {
        return m_last_id++;
    }

    public EventHandle(Command command) {
        this(command, true);
    }

    public EventHandle(Command command, boolean interruptible) {
        this(stack -> {
            command.schedule(interruptible);
        });
    }

    public EventHandle(RunnableEvent target) {
        this(stack -> {
            target.fire();
        });
    }

    public EventHandle(RunnableStackedEvent target) {
        if (target == null)
            throw new InvalidHandleException("Attempted to construct handle with null parameter.");

        m_id = obtainId();

        m_target = target;
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
    
    protected RunnableStackedEvent getTarget() {
        return m_target;
    }


    @Override
    public boolean equals(Object obj) {
        return ((EventHandle) obj).m_id == this.m_id;
    }
}