package org.frc.team5409.churro.control;

final class SendableEvent {
    protected final EventHandle handle;
    protected final EventStack  stack;

    public SendableEvent(EventHandle handle, EventStack stack) {
        this.handle = handle;
        this.stack = stack;
    }
}