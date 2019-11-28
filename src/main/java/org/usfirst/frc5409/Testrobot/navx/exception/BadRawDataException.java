package org.usfirst.frc5409.Testrobot.navx.exception;

import org.usfirst.frc5409.Testrobot.navx.NavXData;

/**
 * Expection thrown when trying to convert raw byte data
 * to a NavX data container when the data is invalid
 * 
 * @author Keith Davies
 * 
 * @see NavXData
 */
public final class BadRawDataException extends RuntimeException {
    /**
     * {@inheritDoc}
     */
    public BadRawDataException(String message) {
        super(message);
    }
    
    /**
     * {@inheritDoc}
     */
    public BadRawDataException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * {@inheritDoc}
     */
    public BadRawDataException(Throwable cause) {
        super(cause);
    }
    
}