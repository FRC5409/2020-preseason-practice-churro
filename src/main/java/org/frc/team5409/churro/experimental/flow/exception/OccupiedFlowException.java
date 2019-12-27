package org.frc.team5409.churro.experimental.flow.exception;

public class OccupiedFlowException extends RuntimeException {
    /**
    * {@inheritDoc}
    */
    public OccupiedFlowException(String message) {
        super(message);
    }
    
    /**
    * {@inheritDoc}
    */
    public OccupiedFlowException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
    * {@inheritDoc}
    */
    public OccupiedFlowException(Throwable cause) {
        super(cause);
    }
}