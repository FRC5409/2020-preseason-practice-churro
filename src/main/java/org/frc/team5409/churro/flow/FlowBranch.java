package org.frc.team5409.churro.flow;

import org.frc.team5409.churro.flow.exception.*;

public class FlowBranch extends FlowBase {
    protected FlowNode m_nodes[];
    private   boolean  m_finished;

    public FlowBranch() {
        m_finished = false;
        m_nodes    = new FlowNode[0];
    }

    public FlowBranch add(FlowNode node) {
        if (m_finished)
            throw new BranchClosedException("Attempted to add node to closed branch.");

        System.arraycopy(m_nodes, 0, m_nodes, 0, m_nodes.length+1);

        m_nodes[m_nodes.length-1] = node;

        return this;
    }

    public final void finish() {
        if (m_nodes.length == 0)
            throw new EmptyFlowException("Attempted to close branch with no nodes.");
        else if (m_finished)
            return;
        
        m_in_type = m_nodes[0].m_in_type;
        m_out_type = m_nodes[m_nodes.length-1].m_out_type;

        m_finished = true;
    }

    @Override
    protected <I, O> FlowObject<O> flow(FlowObject<I> in) {
        if (!m_finished)
            throw new OpenFlowException("Attempted to flow unfinished/open branch. Please close branch.");

        checkIn(in); //TODO: Check safety

        FlowObject data = new FlowObject<I>(in);

        for (int i=0; i<m_nodes.length; i++)
            data = m_nodes[i].flow(data);

        return checkOut(data);
    };
}