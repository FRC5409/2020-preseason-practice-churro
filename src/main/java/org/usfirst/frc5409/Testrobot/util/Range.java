package org.usfirst.frc5409.Testrobot.util;

//Numerical range, very unsafe generic class
public class Range<T extends Number> {
    public final T min;
    public final T max;

    public Range(T min, T max) {
        this.min = min;
        this.max = max;
    }
}
