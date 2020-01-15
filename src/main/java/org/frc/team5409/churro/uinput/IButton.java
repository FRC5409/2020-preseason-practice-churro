package org.frc.team5409.churro.uinput;

import java.util.concurrent.atomic.AtomicBoolean;

import org.frc.team5409.churro.control.*;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public final class IButton {
    private AtomicBoolean m_state;
    private AtomicBoolean m_latch_state;
    private double        m_last_press;

    private EventEmitter  m_on_pressed;
    private EventEmitter  m_on_double_pressed;
    private EventEmitter  m_while_pressed;
    private EventEmitter  m_on_released;
    private EventEmitter  m_while_released;

    protected IButton() {
        m_state = new AtomicBoolean(false);
        m_latch_state = new AtomicBoolean(false);
        m_last_press = -1;

        m_on_pressed = new EventEmitter();
        m_on_double_pressed = new EventEmitter();
        m_while_pressed = new EventEmitter();
        m_on_released = new EventEmitter();
        m_while_released = new EventEmitter();
    }

    public boolean isPressed() {
        return m_state.get();
    }

    public EventHandle whenPressed(Command command) {
        return newHandle(m_on_pressed, command);
    }

    public EventHandle whenDoublePressed(Command command) {
        return newHandle(m_on_double_pressed, command);
    }

    public EventHandle whilePressed(Command command) {
        return newHandle(m_while_pressed, command.withInterrupt(() -> { return !isPressed(); }));
    }
    
    public EventHandle whilePressedLatch(Command command) {
        return newLatchHandle(m_on_pressed, command);
    }

    public EventHandle whenReleased(Command command) {
        return newHandle(m_while_pressed, command);
    }

    public EventHandle whileReleased(Command command) {
        return newHandle(m_while_pressed, command.withInterrupt(() -> { return isPressed(); }));
    }

    public EventHandle whileReleasedLatch(Command command) {
        return newLatchHandle(m_on_released, command);
    }

    protected void query(boolean is_pressed) {
       boolean last_state = m_state.getAndSet(is_pressed);

        if (last_state == false && is_pressed == true) {
            m_latch_state.set(!m_latch_state.get());

            double now_press = Timer.getFPGATimestamp();
            if (now_press-m_last_press < 0.5d) 
                m_on_double_pressed.emit();
            else
                m_on_pressed.emit();
            m_last_press = now_press;
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

    private EventHandle newLatchHandle(EventEmitter emitter, Command command) {
        final boolean last_latch_state = m_latch_state.get();
        EventHandle handle = new EventHandle(() -> {
            if (last_latch_state != m_latch_state.get())
                command.schedule(true);
            else
                command.cancel();
        });
        handle.bind(emitter);
        return handle;
    }
}