package org.frc.team5409.churro.control;

public abstract class EventHandle {
    private static long m_last_id = Long.MIN_VALUE;

    private final  long m_id;

    protected EventHandle() {
        m_id = obtainId();
    }
    
    protected abstract void bind(EventEmitter emitter);

    public abstract void unbind();
    
    @Override
    public boolean equals(Object obj) {
        return ((EventHandle) obj).m_id == this.m_id;
    }
    
    private static synchronized long obtainId() {
        return m_last_id++;
    }
}