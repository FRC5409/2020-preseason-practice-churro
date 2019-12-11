package org.frc.team5409.churro.flow;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.frc.team5409.churro.flow.exception.NetworkClosedException;

public abstract class FlowNetwork {
    private ExecutorService m_task_pool;
    private boolean         m_closed;

    public FlowNetwork() {
        m_task_pool = Executors.newCachedThreadPool();
        m_closed = false;
    }

    protected void flow(FlowNode node, FlowObject drop) {
        if (m_closed)
            throw new NetworkClosedException("Attempted to flow through node on closed network. Check Code.");
        
        m_task_pool.submit(new FlowTask(node, drop));
    }

    protected void flow(FlowNode nodes[], FlowObject drop) {
        if (m_closed)
            throw new NetworkClosedException("Attempted to flow through nodes on closed network. Check Code.");
        
        for (int i=0; i<nodes.length; i++)
            m_task_pool.submit(new FlowTask(nodes[i], drop));
    }

    protected final void close() {
        if (m_closed)
            return;
        
        m_task_pool.shutdown();
        m_closed = true;
    }
}