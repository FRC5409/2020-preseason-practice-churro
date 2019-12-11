package org.frc.team5409.churro.flow;

public abstract class FlowListener<T, R> extends FlowNode<T, R> {
    public final FlowListener<T, R> listenTo(FlowNode provider) {
        int child_index = provider.addChild(this);
        this.addParent(provider, child_index);

        return this;
    };
}