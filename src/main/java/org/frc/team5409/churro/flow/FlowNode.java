package org.frc.team5409.churro.flow;

import java.util.Arrays;

import org.frc.team5409.churro.flow.exception.FlowException;

public abstract class FlowNode<T, R> {
    private final FlowType<T> m_flow_type = new FlowType<T>();
    private final FlowType<R> m_drip_type = new FlowType<R>();

    private FlowNetwork       m_network = null;

    private FlowNode          m_children[];
    private FlowNode          m_parent = null;

    private int               m_this_index = NO_INDEX;
    private int               m_num_children = 0;

    protected abstract void onFlow(FlowObject<T> drop);

    protected final synchronized int addChild(FlowNode child) {
        if (child.m_parent != null)
            throw new FlowException("Attempted to adopt an already parented node.");
        else if (child.m_network != null)
            throw new FlowException("Attempted to adopt a child of a different network.");

        m_num_children++;

        if (m_num_children > m_children.length)
            m_children = Arrays.copyOf(m_children, m_num_children);

        m_children[m_num_children-1] = child;
        child.m_network = m_network;

        return m_num_children-1;
    }

    protected final synchronized void addParent(FlowNode parent, int child_index) {
        if (m_parent != null)
            throw new FlowException("Attempted child a parent node when child already has a parent.");
        
        m_parent = parent;
        m_this_index = child_index;
    }

    protected final synchronized void joinNetwork(FlowNetwork network) {
        if (m_network != null)
            throw new FlowException("Attempted join a network when node was already apart of one.");
        m_network = network;
    }

    public final synchronized void flow(FlowObject<T> drop) {
        onFlow(drop);

        if (m_children.length != 0)
            m_network.flow(m_children, drop);
    }

    public final synchronized void flow(FlowNode node, FlowObject<T> drop) {
        onFlow(drop);

        if (m_children.length != 0)
            m_network.flow(node, drop);
    }

    public final FlowType<T> getFlowType() {
        return m_flow_type;
    }

    public final FlowType<R> getDripType() {
        return m_drip_type;
    }

    private static final int NO_INDEX = -1;
}