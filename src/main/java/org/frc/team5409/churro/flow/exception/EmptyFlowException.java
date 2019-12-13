package org.frc.team5409.churro.flow.exception;

public class EmptyFlowException extends FlowException {
    /**
    * {@inheritDoc}
    */
    public EmptyFlowException(String message) {
        super(message);
    }
    
    /**
    * {@inheritDoc}
    */
    public EmptyFlowException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
    * {@inheritDoc}
    */
    public EmptyFlowException(Throwable cause) {
        super(cause);
    }
}