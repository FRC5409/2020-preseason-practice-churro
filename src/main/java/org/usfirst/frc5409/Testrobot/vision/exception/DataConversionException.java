package org.usfirst.frc5409.Testrobot.vision.exception;

public final class DataConversionException extends RuntimeException {
    /**
    * {@inheritDoc}
    */
    public DataConversionException(String message) {
        super(message);
    }
    
    /**
    * {@inheritDoc}
    */
    public DataConversionException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
    * {@inheritDoc}
    */
    public DataConversionException(Throwable cause) {
        super(cause);
    }
}