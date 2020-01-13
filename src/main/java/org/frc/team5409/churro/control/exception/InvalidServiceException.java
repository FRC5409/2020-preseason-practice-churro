package org.frc.team5409.churro.control.exception;

public final class InvalidServiceException extends ControlException {
    private static final long serialVersionUID = 7585233785747977663L;

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