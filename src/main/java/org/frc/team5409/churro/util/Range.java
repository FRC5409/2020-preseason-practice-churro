package org.frc.team5409.churro.util;

public final class Range {
    private double m_min;
    private double m_max;

    public Range(double min, double max) {
        m_min = min;
        m_max = max;
    }

    public void setRange(double min, double max) {
        m_min = min;
        m_max = max;
    }

    public double clamp(double value) {
        return clamp(m_min, value, m_max);
    }

    public static double clamp(double min, double value, double max) {
        if (value > max)
            return max;
        else if (value < min)
            return min;
        return value;
    }
}