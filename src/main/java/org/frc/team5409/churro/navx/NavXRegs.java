package org.frc.team5409.churro.navx;

/**
 * Simple structure for specifying a group
 * of registers on the NavX system.
 */
final class NavXRegs {
    /**
     * Constructs Regs container.
     * 
     * @param rx Starting register
     * @param ex Ending register
     */
    public NavXRegs(int rx, int ex) {
        this.rx = (byte) rx;
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