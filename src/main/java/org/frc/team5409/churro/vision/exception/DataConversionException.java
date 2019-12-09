package org.frc.team5409.churro.vision.exception;

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