package org.frc.team5409.churro.flow;

public abstract class FlowObject<T> {
    private final FlowType<T> m_type  = new FlowType<T>();

    T object;

    public FlowObject(T copy) {
        object = copy;
    }
    
    public final FlowType<T> getType() {
        return m_type;
    }
}