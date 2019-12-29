package org.frc.team5409.churro.control.exception;

public final class InvalidServiceException extends ControlException {
    /**
     * {@inheritDoc}
     */
    public InvalidServiceException(String message) {
        super(message);
    }
    
    /**
     * {@inheritDoc}
     */
    public InvalidServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * {@inheritDoc}
     */
    public InvalidServiceException(Throwable cause) {
        super(cause);
    }
    
}