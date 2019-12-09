package org.frc.team5409.churro.vision.exception;

public final class LockedStructureException extends RuntimeException {
    /**
    * {@inheritDoc}
    */
    public LockedStructureException(String message) {
        super(message);
    }
    
    /**
    * {@inheritDoc}
    */
    public LockedStructureException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
    * {@inheritDoc}
    */
    public LockedStructureException(Throwable cause) {
        super(cause);
    }
}