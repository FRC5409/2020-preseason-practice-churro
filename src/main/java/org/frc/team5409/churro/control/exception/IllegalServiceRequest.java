package org.frc.team5409.churro.control.exception;

public final class IllegalServiceRequest extends ControlException {
    private static final long serialVersionUID = -6417812979688937980L;

    /**
     * {@inheritDoc}
     */
    public IllegalServiceRequest(String message) {
        super(message);
    }
    
    /**
     * {@inheritDoc}
     */
    public IllegalServiceRequest(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * {@inheritDoc}
     */
    public IllegalServiceRequest(Throwable cause) {
        super(cause);
    }
    
}