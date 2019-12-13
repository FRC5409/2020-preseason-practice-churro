package org.frc.team5409.churro.flow;

public final class FlowObject<T> {
    private final FlowType<T> m_type = new FlowType<T>();

    T object;

    public FlowObject() {
        
    }

    public FlowObject(T object) {
        this.object = object;
    }

    public FlowObject(FlowObject<T> copy) {
        this.object = copy.object;
    }
    
    public final FlowType<T> getType() {
        return m_type;
    }
}