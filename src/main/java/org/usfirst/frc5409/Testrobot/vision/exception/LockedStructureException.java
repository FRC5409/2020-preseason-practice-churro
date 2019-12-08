package org.usfirst.frc5409.Testrobot.vision.exception;

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