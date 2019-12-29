package org.frc.team5409.churro.control.exception;

public class CallSecurityException extends ControlException {
    /**
     * {@inheritDoc}
     */
    public CallSecurityException(String message) {
        super(message);
    }
    
    /**
     * {@inheritDoc}
     */
    public CallSecurityException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * {@inheritDoc}
     */
    public CallSecurityException(Throwable cause) {
        super(cause);
    }
    
}