package org.frc.team5409.churro.flow.exception;

public class FlowPolicyException extends FlowException {
    /**
    * {@inheritDoc}
    */
    public FlowPolicyException(String message) {
        super(message);
    }
    
    /**
    * {@inheritDoc}
    */
    public FlowPolicyException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
    * {@inheritDoc}
    */
    public FlowPolicyException(Throwable cause) {
        super(cause);
    }
}