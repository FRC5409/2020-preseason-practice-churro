package org.usfirst.frc5409.Testrobot.navx;

final class Regs {
    public Regs(byte rx, byte ex) {
        this.rx = rx;
        this.nx = (byte) (ex-rx);
    }
    public final byte rx; //Starting register
    public final byte nx; //Num of registers
};