package org.frc.team5409.churro.flow;

import org.frc.team5409.churro.flow.exception.FlowTypeException;

abstract class FlowBase {
    protected FlowType<?> m_in_type;
    protected FlowType<?> m_out_type;

    protected abstract <I,O> FlowObject<O> flow(FlowObject<I> in); 

    public final FlowType<?> getIType() {
        return m_in_type;
    }

    public final FlowType<?> getOType() {
        return m_out_type;
    }

    protected final <I> FlowObject<I> checkIn(FlowObject<I> in) {
        if (m_in_type.isAssignableFrom(in.getType()))
            return in;
        else
            throw new FlowTypeException(//TODO: Get a better error message
                String.format("Attempted undefined conversion of flow object %s to %s.", m_in_type.getName(), in.getType().getName()));
    }

    protected final <O> FlowObject<O> checkOut(FlowObject<O> out) {
        if (m_out_type.isAssignableFrom(out.getType()))
            return out;
        else
            throw new FlowTypeException(//TODO: Get a better error message
                String.format("Attempted undefined conversion of flow object %s to %s.", m_in_type.getName(), out.getType().getName()));
    }

    protected final <I, _I> I safeIn(FlowObject<_I> in) {
        if (m_in_type.isAssignableFrom(in.getType()))//TODO: See if a type check on 'FlowType<I>' vs 'm_in_type' would be needed...
            return (I) (m_in_type.cast(in.object)); //Because type 'I' and the 'cast' SHOULD be the same type, I dunno....
        else {
            throw new FlowTypeException(
                String.format("Attempted undefined conversion of flow type %s to %s.", m_in_type.getName(), in.getType().getName()));
        }
    }

    protected final <_O> FlowObject<?> safeOut(_O out) {
        if (m_out_type.isAssignableFrom(FlowType.T(out)))
            return FlowType.newObject(m_out_type, m_out_type.cast(out));
        else {
            throw new FlowTypeException(
                String.format("Attempted undefined conversion of flow type %s to %s.", m_out_type.getName(), (FlowType.T(out)).getName()));
        }
    }

}