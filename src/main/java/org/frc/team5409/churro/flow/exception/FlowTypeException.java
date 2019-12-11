package org.frc.team5409.churro.flow.exception;

public class FlowTypeException extends FlowException {
    /**
    * {@inheritDoc}
    */
    public FlowTypeException(String message) {
        super(message);
    }
    
    /**
    * {@inheritDoc}
    */
    public FlowTypeException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
    * {@inheritDoc}
    */
    public FlowTypeException(Throwable cause) {
        super(cause);
    }
}