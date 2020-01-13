package org.frc.team5409.churro.control.exception;

public final class InvalidHandleException extends ControlException {
    private static final long serialVersionUID = -6483640062430448710L;

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