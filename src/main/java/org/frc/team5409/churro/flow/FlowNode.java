package org.frc.team5409.churro.flow;

public abstract class FlowNode {
    protected FlowNode    m_children[];
    protected FlowNode    m_parent;

    private   FlowNetwork m_network;

    protected <T> void onDrip(FlowObject<T> drip) {};

    protected <T> void flow(FlowObject<T> drop) {
        m_network.flow(m_children, drop);
    }
}