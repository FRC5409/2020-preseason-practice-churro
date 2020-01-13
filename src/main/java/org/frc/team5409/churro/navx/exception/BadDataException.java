package org.frc.team5409.churro.navx.exception;

/**
 * Expection thrown when trying to convert raw byte data
 * to a NavX data container when the data is invalid
 * 
 * @author Keith Davies
 * 
 * @see org.frc.team5409.churro.navx.NavXData
 */
public final class BadDataException extends RuntimeException {
    private static final long serialVersionUID = 7286084801556540024L;

    /**
     * {@inheritDoc}
     */
    public BadDataException(String message) {
        super(message);
    }
    
    /**
     * {@inheritDoc}
     */
    public BadDataException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * {@inheritDoc}
     */
    public BadDataException(Throwable cause) {
        super(cause);
    }
    
}