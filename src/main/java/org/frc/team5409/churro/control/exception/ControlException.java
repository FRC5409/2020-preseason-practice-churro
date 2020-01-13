package org.frc.team5409.churro.control.exception;

public class ControlException extends RuntimeException {
    private static final long serialVersionUID = -1949827286713529703L;

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