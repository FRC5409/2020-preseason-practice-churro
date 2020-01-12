package org.frc.team5409.churro.control;

@FunctionalInterface
public abstract interface RunnableStackedEvent {
    public void fire(EventStack stack);
}