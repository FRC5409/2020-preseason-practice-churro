package org.frc.team5409.churro.navx.exception;

import org.frc.team5409.churro.navx.NavXData;

/**
 * Expection thrown when trying to convert raw byte data
 * to a NavX data container when the data is invalid
 * 
 * @author Keith Davies
 * 
 * @see NavXData
 */
public final class RawDataException extends RuntimeException {
    /**
     * {@inheritDoc}
     */
    public RawDataException(String message) {
        super(message);
    }
    
    /**
     * {@inheritDoc}
     */
    public RawDataException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * {@inheritDoc}
     */
    public RawDataException(Throwable cause) {
        super(cause);
    }
    
}