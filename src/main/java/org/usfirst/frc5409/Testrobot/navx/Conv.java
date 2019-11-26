package org.usfirst.frc5409.Testrobot.navx;

/**
 * Raw data conversion functions.
 * THESE FUNCTIONS NEED TO BE TESTED
 * [Raw bytes] -> [Java type]
 */
final class Conv {
    /**
     * Decode Unsigned Byte. (8-bit)
     * 
     * @param data Raw byte data
     * @param i Index offset
     * 
     * @return Unsigned Byte
     */
    public static short decodeUnsignedByte(byte data[], int i) { 
        return (short) (data[i]);
    }

    /**
     * Decode Unsigned Byte. (8-bit)
     * 
     * @param data Raw byte
     * @param i Index offset
     * 
     * @return Unsigned Byte
     */
    public static short decodeUnsignedByte(byte data) { //Test this
        return (short) (data);
    }

    /**
     * Decode Unsigned Short. (16-bit)
     * 
     * @param data Raw byte data
     * @param i Index offset
     * 
     * @return Unsigned short
     */
    public static int decodeUnsignedShort(byte data[], int i) { //Test this
        return (int) ( 
            (data[i    ] << 8) | 
            (data[i + 1] << 0)
        );
    }

    /**
     * Decode Signed Integer. (32-bit)
     * 
     * @param data Raw byte data
     * @param i Index offset
     * 
     * @return Signed Integer
     */
    public static int decodeSignedInt(byte data[], int i) {
        return (int) ( 
            (data[i + 0] << 24) |
            (data[i + 1] << 16) |
            (data[i + 2] << 8 ) |
            (data[i + 3] << 0 )
        );
    }

    /**
     * Decode Unsigned Integer. (32-bit)
     * 
     * @param data Raw byte data
     * @param i Index offset
     * 
     * @return Unsigned Integer
     */
    public static long decodeUnsignedInt(byte data[], int i) { //Test this
        return (int) ( 
            (data[i + 0] << 24) |
            (data[i + 1] << 16) |
            (data[i + 2] << 8 ) |
            (data[i + 3] << 0 )
        );
    }

    /**
     * Decode Signed Hundredths. (-327.68 to 327.67)
     * 
     * @param data Raw byte data
     * @param i Index offset
     * 
     * @return Double floating-point Number
     */
    public static double decodeSignedHundredths(byte data[], int i) {
        return (double) (decodeSignedShort(data, i)) / 100.0d;
    }

    /**
     * Decode Unsigned Hundredths. (0.0 to 655.35)
     * 
     * @param data Raw byte data
     * @param i Index offset
     * 
     * @return Double floating-point Number
     */
    public static double decodeUnsignedHundredths(byte data[], int i) {
        return (double) (decodeUnsignedShort(data, i)) / 100.0d;
    }

    /**
     * Decode Signed Thousandths. (-32.768 to 32.767)
     * 
     * @param data Raw byte data
     * @param i Index offset
     * 
     * @return Double floating-point Number
     */
    public static double decodeSignedThousandths(byte data[], int i) {
        return (double) (decodeSignedShort(data, i)) / 1000.0d;
    }

    /**
     * Decode Signed Pi. (-2 to 2)
     * 
     * @param data Raw byte data
     * @param i Index offset
     * 
     * @return Double floating-point Number
     */
    public static double decodeSignedPiRadians(byte data[], int i) {
        return (double) (decodeSignedShort(data, i)) / 16384.0d;
    }

    /**
     * Decode Q16.16 Number. (-32768.9999 to 32767.9999)
     * 
     * @param data Raw byte data
     * @param i Index offset
     * 
     * @return Double floating-point Number
     */
    public static double decodeQ1616(byte data[], int i) {
        return (double) (decodeSignedInt(data, i)) / 65536.0d;
    }
}