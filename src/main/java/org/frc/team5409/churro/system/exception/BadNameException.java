package org.frc.team5409.churro.system.exception;

public class BadNameException extends RuntimeException {
    private static final long serialVersionUID = 400805224324655798L;

    /**
     * {@inheritDoc}
     */
    public BadNameException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public BadNameException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     */
    public BadNameException(Throwable cause) {
        super(cause);
    }
    
}