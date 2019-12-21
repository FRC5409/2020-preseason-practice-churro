package org.frc.team5409.churro.flow;

final class FlowType<_T> {
    private Class<_T> raw;

    /*public <R> T cast(R other) {
        return (T) other;
    }*/    

    public String getName() {
        return raw.getSimpleName();
    }

    public boolean isAssignableFrom(FlowType<?> other) {
        return raw.isAssignableFrom(other.raw);
    }

    /*public boolean isNull() {
        return raw.isAssignableFrom(FlowNull.class);
    }*/

    public static <R> FlowType<R> T(R in) {
        return new FlowType<R>();
    }

    public static <R> FlowType<R> T(Class<R> in) {
        return new FlowType<R>();
    }
}