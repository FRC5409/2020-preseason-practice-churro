package org.frc.team5409.churro.flow;

public abstract class FlowPartaker<T, R> extends FlowNode<T, R> {
    public final FlowPartaker<T, R> listenTo(FlowNode provider) {
        int child_index = provider.addChild(this);
        this.addParent(provider, child_index);

        return this;
    };

    public final FlowPartaker<T, R> provideFor(FlowNode listener) {
        int child_index = this.addChild(listener);
        listener.addParent(this, child_index);

        return this;
    };
}