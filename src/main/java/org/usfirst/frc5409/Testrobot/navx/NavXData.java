package org.usfirst.frc5409.Testrobot.navx;

/**
 * Abstract class from which all NavX
 * data containers must inherit from.
 * Provides useful functionality like
 * construction from raw byte data, etc.
 * 
 * @author Keith Davies
 */
public abstract class NavXData {
    /**
     * Construct blank NavX data container.
     */
    public NavXData() {

    }

    /**
     * Construct container from raw byte data
     * retrieved from NavX registers.
     * 
     * @param raw Raw Byte Data
     * 
     * @return Constructed data container.
     */
    public static NavXData fromRaw(byte raw_data[]) {
        return null;
    };
}