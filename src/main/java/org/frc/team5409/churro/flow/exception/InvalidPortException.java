package org.frc.team5409.churro.flow.exception;

public class InvalidPortException extends RuntimeException {
    /**
    * {@inheritDoc}
    */
    public InvalidPortException(String message) {
        super(message);
    }
    
    /**
    * {@inheritDoc}
    */
    public InvalidPortException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
    * {@inheritDoc}
    */
    public InvalidPortException(Throwable cause) {
        super(cause);
    }
}