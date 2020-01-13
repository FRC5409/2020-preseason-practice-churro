package org.frc.team5409.churro.control.exception;

public final class StackCastException extends ControlException {
    private static final long serialVersionUID = 5198637277982120152L;

    /**
     * {@inheritDoc}
     */
    public StackCastException(String message) {
        super(message);
    }
    
    /**
     * {@inheritDoc}
     */
    public StackCastException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * {@inheritDoc}
     */
    public StackCastException(Throwable cause) {
        super(cause);
    }
    
}