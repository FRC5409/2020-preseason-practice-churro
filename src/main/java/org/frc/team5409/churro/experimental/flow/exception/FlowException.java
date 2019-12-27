package org.frc.team5409.churro.experimental.flow.exception;

public class FlowException extends RuntimeException {
    /**
    * {@inheritDoc}
    */
    public FlowException(String message) {
        super(message);
    }
    
    /**
    * {@inheritDoc}
    */
    public FlowException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
    * {@inheritDoc}
    */
    public FlowException(Throwable cause) {
        super(cause);
    }
}