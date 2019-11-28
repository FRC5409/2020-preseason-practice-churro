package org.usfirst.frc5409.Testrobot.navx;

import org.usfirst.frc5409.Testrobot.navx.exception.RawDataException;

/**
 * Abstract class from which all NavX data containers must
 * inherit and implement from. Provides useful functionality
 * like construction from raw byte data, data check, etc.
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
     * @param raw Raw byte Data
     * 
     * @return Constructed data container.
     */
    public static NavXData fromRaw(byte raw_data[]) {
        return null;
    };

    /**
     * Safety check for ensuring proper data
     * is being fed into NavX container
     * 
     * @param raw_data Raw byte data
     * 
     * @throws BadRawDataException Thrown when data is invalid
     */
    protected static void check(byte raw_data[], int expected_len) throws RawDataException {
        if (raw_data.length != expected_len)
            throw new RawDataException(String.format("Invalid Raw Data buffer length. Got \"%d\", expected \"%d\"", raw_data.length, expected_len));
    }
    /**
     * Length of raw data buffer.
     */
    protected static final int m_raw_data_length = -1;
}