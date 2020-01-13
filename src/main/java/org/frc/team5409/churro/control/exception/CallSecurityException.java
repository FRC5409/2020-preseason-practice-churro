package org.frc.team5409.churro.control.exception;

public class CallSecurityException extends ControlException {
    private static final long serialVersionUID = 969765243555895718L;

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