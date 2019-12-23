package org.frc.team5409.churro.flow.exception;

public class FlowIdentityException extends FlowException {
    /**
    * {@inheritDoc}
    */
    public FlowIdentityException(String message) {
        super(message);
    }
    
    /**
    * {@inheritDoc}
    */
    public FlowIdentityException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
    * {@inheritDoc}
    */
    public FlowIdentityException(Throwable cause) {
        super(cause);
    }
}