package org.usfirst.frc5409.Testrobot.navx;

/**
 * Simple class for specifying a group
 * of registers on the NavX system.
 */
final class Regs {
    /**
     * Constructs Regs container.
     * 
     * @param rx Starting register
     * @param ex Ending register
     */
    public Regs(byte rx, byte ex) {
        this.rx = rx;
        this.nx = (byte) (ex-rx);
    }

    /**
     * Starting register
     */
    public final byte rx;

    /**
     * Number of subsequent registers
     */
    public final byte nx;
};