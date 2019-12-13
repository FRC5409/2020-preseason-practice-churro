package org.frc.team5409.churro.flow;

public abstract class FlowNode<_I, _O> extends FlowBase {
    protected final FlowType<_I> m_in_type = new FlowType<_I>();
    protected final FlowType<_O> m_out_type = new FlowType<_O>();
}