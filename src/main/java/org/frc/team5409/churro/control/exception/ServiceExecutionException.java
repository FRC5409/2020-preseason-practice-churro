package org.frc.team5409.churro.control.exception;

public class ServiceExecutionException extends ControlException {
    private static final long serialVersionUID = 8375030424153521516L;

    /**
     * {@inheritDoc}
     */
    public ServiceExecutionException(String message) {
        super(message);
    }
    
    /**
     * {@inheritDoc}
     */
    public ServiceExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * {@inheritDoc}
     */
    public ServiceExecutionException(Throwable cause) {
        super(cause);
    }
    
}