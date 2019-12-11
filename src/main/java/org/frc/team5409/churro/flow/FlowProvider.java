package org.frc.team5409.churro.flow;

public abstract class FlowProvider<T, R> extends FlowNode<T, R> {
    public final FlowProvider<T, R> provideFor(FlowNode listener) {
        int child_index = this.addChild(listener);
        listener.addParent(this, child_index);

        return this;
    };
}