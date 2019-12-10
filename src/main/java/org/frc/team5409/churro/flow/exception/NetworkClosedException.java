package org.frc.team5409.churro.flow.exception;

public final class NetworkClosedException extends RuntimeException {
    /**
    * {@inheritDoc}
    */
    public NetworkClosedException(String message) {
        super(message);
    }
    
    /**
    * {@inheritDoc}
    */
    public NetworkClosedException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
    * {@inheritDoc}
    */
    public NetworkClosedException(Throwable cause) {
        super(cause);
    }
}