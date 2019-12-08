package org.usfirst.frc5409.Testrobot.vision.exception;

public final class NoStepsException extends RuntimeException {
    /**
    * {@inheritDoc}
    */
    public NoStepsException(String message) {
        super(message);
    }
    
    /**
    * {@inheritDoc}
    */
    public NoStepsException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
    * {@inheritDoc}
    */
    public NoStepsException(Throwable cause) {
        super(cause);
    }
}