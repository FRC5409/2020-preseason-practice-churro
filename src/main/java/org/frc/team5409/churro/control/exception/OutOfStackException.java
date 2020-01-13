package org.frc.team5409.churro.control.exception;

public final class OutOfStackException extends ControlException {
    private static final long serialVersionUID = 2759345907058452526L;

    /**
     * {@inheritDoc}
     */
    public OutOfStackException(String message) {
        super(message);
    }
    
    /**
     * {@inheritDoc}
     */
    public OutOfStackException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * {@inheritDoc}
     */
    public OutOfStackException(Throwable cause) {
        super(cause);
    }
    
}