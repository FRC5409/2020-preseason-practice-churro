package org.frc.team5409.churro.navx;

/**
 * Provides details for specfic failures that may
 * in the communication process.
 */
public enum ComResult {
    /**
     * Communication Success
     */
    SUCCESS(true),

    /**
     * Communication Failure. (Handshake failed)
     */
    FAILED(false),

    /**
     * Communication Failure due to
     * bad CRC. Data that came back 
     * failed corruption check.
     */
    BADCRC(false),

    /**
     * Communication Failure due to
     * bad register address.
     */
    NOADDRESS(false),
    
    /**
     * Communication with NavX is
     * nonexsistent.
     */
    NOCONNECTION(false);

    private ComResult(boolean bool) {
        this.success = bool;
    }

    /**
     * Boolean result.
     * Whether communication succeeded or not.
     */
    public final boolean success;
}