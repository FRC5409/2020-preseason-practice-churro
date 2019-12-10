package org.frc.team5409.churro.flow;

class FlowTask<T> implements Runnable {
    private FlowObject<T>  m_drop;
    private FlowNode       m_node;

    public FlowTask(FlowNode node, FlowObject<T> drop) {
        m_node = node;
        m_drop = drop;
    }

    @Override
    public void run() {
        m_node.flow(m_drop);
    }
}