package org.frc.team5409.churro.control;

import org.frc.team5409.churro.control.exception.*;

public final class EventStack {
    private final Object m_stack[];

    @SafeVarargs
    protected <T> EventStack(T... stack) {
        m_stack = stack;
    }
    
    public Object[] getStack() {
        return m_stack;
    }

    @SuppressWarnings("unchecked")
    public <R> R get(int index) {
        if (index > m_stack.length-1)
            throw new OutOfStackException("Attempted to access object outside of EventStack.");

        try {
            return (R) m_stack[index];
        } catch (ClassCastException e) {
            throw new StackCastException("Stack cast exception at index "+index+".",  e);
        }
    }
}