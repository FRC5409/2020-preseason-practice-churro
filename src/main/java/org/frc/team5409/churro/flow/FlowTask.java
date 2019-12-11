package org.frc.team5409.churro.flow;

import org.frc.team5409.churro.flow.exception.FlowTypeException;

class FlowTask implements Runnable {
    private FlowObject m_drop;
    private FlowNode   m_node;

    public FlowTask(FlowNode node, FlowObject drop) {
        m_node = node;
        m_drop = drop;
    }

    @Override
    public void run() {
        assertTypes(m_node.getDripType(), m_drop.getType());
        
        m_node.flow(m_drop);
    }

    private static final <T,R> void assertTypes(FlowType<T> type_a, FlowType<R> type_b) {
        if (!type_a.clazz.isAssignableFrom(type_b.clazz))
            throw new FlowTypeException(String.format("Attempted to flow %s type through non-convertible %s type. Check Code",
                type_a.clazz.getSimpleName(), 
                    type_b.clazz.getSimpleName()));
    }
}