package org.frc.team5409.churro.flow.exception;

public class FlowConfigException extends RuntimeException {
    /**
     * {@inheritDoc}
     */
    public FlowConfigException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public FlowConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     */
    public FlowConfigException(Throwable cause) {
        super(cause);
    }
}