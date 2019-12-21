package org.frc.team5409.churro.flow;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.frc.team5409.churro.flow.exception.*;

public abstract class FlowPoint {
    protected FlowType<?> m_flow_params[] = null;
    protected Method      m_flow_func = null;

    protected FlowPoint(Class<?>... types) {
        register(types);
    }

    protected FlowPoint(String name, Class<?>... params) {
        register(name, params);
    }

    protected void register(String name, Class<?>... params) {
        try {
            m_flow_func = this.getClass().getDeclaredMethod(name, params);
        } catch (NoSuchMethodException e) {
            throw new FlowException(e); // TODO: need error message here
        }
        register(params);
    }

    protected void register(Class<?>... types) {
        if (m_flow_params != null)
            throw new EstablishedFlowException("Attempted to re-register function on an already establish FlowPoint.");

        m_flow_params = new FlowType[types.length];

        for (int i = 0; i < types.length; i++) {
            m_flow_params[i] = FlowType.T(types[i]);
        }
    }

    protected <T> void invoke(T... args) {
        try {
            m_flow_func.invoke(null, args);
        } catch (Exception e) {
            throw new FlowException("Flow invocation incountered an error.", e);
        }
    }

    protected final <T> boolean checkT(T... args) {
        if (args.length != m_flow_params.length)
            return false;
        
        for (int i=0; i<m_flow_params.length; i++) {
            if (!m_flow_params[i].isAssignableFrom(FlowType.T(args[i])));
                return false;
        }

        return false;
    }

    protected final boolean checkT(FlowPoint other) {
        if (m_flow_params.length != other.m_flow_params.length)
            return false;
        
        for (int i=0; i<m_flow_params.length; i++) {
            if (!m_flow_params[i].isAssignableFrom(other.m_flow_params[i]))
                return false;
        }

        return false;
    }

    protected final void assertT(FlowPoint other) {
        if (!checkT(other))
            throw new FlowTypeException("message");//TODO: make exception message for this
    }
    
    protected final <T> void assertT(T... args) {
        if (!checkT(args))
            throw new FlowTypeException("message");//TODO: make exception message for this
    }
}