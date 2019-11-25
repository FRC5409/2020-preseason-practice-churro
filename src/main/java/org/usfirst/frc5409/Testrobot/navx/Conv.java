package org.usfirst.frc5409.Testrobot.navx;

/**
 * Raw data conversion functions.
 * THESE FUNCTIONS NEED TO BE TESTED
 * [Raw bytes] -> [Java type]
 */
final class Conv {
    public static short decodeSignedShort(byte data[], int i) { 
        return (short) ( 
            (data[i    ] << 8) | 
            (data[i + 1] << 0)
        );
    }

    public static int decodeUnsignedShort(byte data[], int i) { //Test this
        return (int) ( 
            (data[i    ] << 8) | 
            (data[i + 1] << 0)
        );
    }

    public static int decodeSignedInt(byte data[], int i) {
        return (int) ( 
            (data[i + 0] << 24) |
            (data[i + 1] << 16) |
            (data[i + 2] << 8 ) |
            (data[i + 3] << 0 )
        );
    }

    public static long decodeUnsignedInt(byte data[], int i) { //Test this
        return (int) ( 
            (data[i + 0] << 24) |
            (data[i + 1] << 16) |
            (data[i + 2] << 8 ) |
            (data[i + 3] << 0 )
        );
    }

    public static double decodeSignedHundreths(byte data[], int i) {
        return (double) (decodeSignedShort(data, i)) / 100.0d;
    }

    public static double decodeUnsignedHundreths(byte data[], int i) {
        return (double) (decodeUnsignedShort(data, i)) / 100.0d;
    }

    public static double decodeSignedThousandths(byte data[], int i) {
        return (double) (decodeSignedShort(data, i)) / 1000.0d;
    }

    public static double decodeUnsignedThousandths(byte data[], int i) {
        return (double) (decodeUnsignedShort(data, i)) / 1000.0d;
    }

    public static double decodeSignedPiRadians(byte data[], int i) {
        return (double) (decodeSignedShort(data, i)) / 16384.0d;
    }

    public static double decodeQ1616(byte data[], int i) {
        return (double) (decodeSignedInt(data, i)) / 65536.0d;
    }
}