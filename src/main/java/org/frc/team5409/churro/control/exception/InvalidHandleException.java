package org.frc.team5409.churro.control.exception;

public final class InvalidHandleException extends ControlException {
    /**
     * {@inheritDoc}
     */
    public InvalidHandleException(String message) {
        super(message);
    }
    
    /**
     * {@inheritDoc}
     */
    public InvalidHandleException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * {@inheritDoc}
     */
    public InvalidHandleException(Throwable cause) {
        super(cause);
    }
    
}