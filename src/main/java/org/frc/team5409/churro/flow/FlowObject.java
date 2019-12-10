package org.frc.team5409.churro.flow;

public abstract class FlowObject<T> {
    T object;

    public FlowObject(T copy) {
        object = copy;
    }
}