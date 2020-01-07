package org.frc.team5409.churro.control;

@FunctionalInterface
abstract interface RunnableEvent {
    public void fire(EventStack stack);
}