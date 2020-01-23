package org.frc.team5409.churro.control;

public abstract class GlobalInstance<T> {
    private T m_inst;
    
    public GlobalInstance(T inst) {
        m_inst = inst;
    }

    protected final T getInstance() {
        return m_inst;
    }
}