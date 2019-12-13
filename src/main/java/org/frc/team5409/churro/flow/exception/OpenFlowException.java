package org.frc.team5409.churro.flow.exception;

public final class OpenFlowException extends FlowException {
    /**
    * {@inheritDoc}
    */
    public OpenFlowException(String message) {
        super(message);
    }
    
    /**
    * {@inheritDoc}
    */
    public OpenFlowException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
    * {@inheritDoc}
    */
    public OpenFlowException(Throwable cause) {
        super(cause);
    }
}