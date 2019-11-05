package org.usfirst.frc5409.Testrobot.util;



//Numerical range, very unsafe generic class
public class Range<T> {
    public Range(T min, T max) {
        this.min = min;
        this.max = max;
    }

    public final T min;
    public final T max;
}