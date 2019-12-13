package org.frc.team5409.churro.flow;

final class FlowType<T> {
    protected Class<T> raw;

    public <R> T cast(R other) {
        return (T) other;
    }    

    public String getName() {
        return raw.getSimpleName();
    }

    public boolean isAssignableFrom(FlowType<?> other) {
        return raw.isAssignableFrom(other.raw);
    }

    public boolean isNull() {
        return raw.isAssignableFrom(FlowNull.class);
    }

    public static <R> FlowType<R> T(R in) {
        return new FlowType<R>();
    }

    public static <R> FlowObject<R> newObject(FlowType<R> in) {
        return new FlowObject<R>();
    }
    public static <R, T> FlowObject<R> newObject(FlowType<?> m_out_type, T object) {//TODO: Pretty unsafe
        return new FlowObject<R>((R) object);
    }
}