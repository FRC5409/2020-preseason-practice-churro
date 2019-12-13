package org.frc.team5409.churro.flow.exception;

public final class BranchClosedException extends FlowException {
    /**
    * {@inheritDoc}
    */
    public BranchClosedException(String message) {
        super(message);
    }
    
    /**
    * {@inheritDoc}
    */
    public BranchClosedException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
    * {@inheritDoc}
    */
    public BranchClosedException(Throwable cause) {
        super(cause);
    }
}