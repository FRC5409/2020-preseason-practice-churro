package org.frc.team5409.churro.uinput;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.wpi.first.wpilibj2.command.Command;

import org.frc.team5409.churro.control.*;

public final class ITrigger {
    private static final double     m_press_thresh = 0.2; //TODO: Find the approriate values heres

    private AtomicReference<Double> m_axis;
    private AtomicBoolean           m_state;

    private EventEmitter            m_on_pressed;
    private EventEmitter            m_while_pressed;
    private EventEmitter            m_on_released;
    private EventEmitter            m_while_released;

    protected ITrigger() {
        m_axis = new AtomicReference<>(0.0d);
        m_state = new AtomicBoolean(false);

        m_on_pressed = new EventEmitter();
        m_while_pressed = new EventEmitter();
        m_on_released = new EventEmitter();
        m_while_released = new EventEmitter();
    }

    public double getValue() {
        return m_axis.get();
    }

    public boolean isPressed() {
        return m_state.get();
    }

    public EventHandle onPressed(Command command) {
        return newHandle(m_on_pressed, command);
    }

    public EventHandle whilePressed(Command command) {
        return newHandle(m_while_pressed, command.withInterrupt(() -> { return !isPressed(); }));
    }

    public EventHandle onReleased(Command command) {
        return newHandle(m_while_pressed, command);
    }

    public EventHandle whileReleased(Command command) {
        return newHandle(m_while_pressed, command.withInterrupt(() -> { return isPressed(); }));
    }

    protected void query(double axis) { // TODO: maybe have gesture detection?? No? ok.
        boolean is_pressed = m_axis.getAndSet(axis) > m_press_thresh;
        boolean last_state = m_state.getAndSet(is_pressed);

        if (last_state == false && is_pressed == true) {
            m_on_pressed.emit();
        } else if (last_state == true && is_pressed == true) {
            m_while_pressed.emit();
        } else if (last_state == true && is_pressed == false) {
            m_on_released.emit();
        } else if (last_state == false && is_pressed == false) {
            m_while_released.emit();
        }
    }

    private EventHandle newHandle(EventEmitter emitter, Command command) {
        EventHandle handle = new EventHandle(command);
        handle.bind(emitter);
        return handle;
    }
}