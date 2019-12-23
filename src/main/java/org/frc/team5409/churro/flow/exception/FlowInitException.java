package org.frc.team5409.churro.flow.exception;

public class FlowInitException extends FlowException {
    /**
    * {@inheritDoc}
    */
    public FlowInitException(String message) {
        super(message);
    }
    
    /**
    * {@inheritDoc}
    */
    public FlowInitException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
    * {@inheritDoc}
    */
    public FlowInitException(Throwable cause) {
        super(cause);
    }
}