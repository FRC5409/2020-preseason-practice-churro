package org.frc.team5409.churro.control.exception;

public class ControlException extends RuntimeException {
    /**
     * {@inheritDoc}
     */
    public ControlException(String message) {
        super(message);
    }
    
    /**
     * {@inheritDoc}
     */
    public ControlException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * {@inheritDoc}
     */
    public ControlException(Throwable cause) {
        super(cause);
    }
    
}