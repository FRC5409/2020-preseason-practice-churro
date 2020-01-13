package org.frc.team5409.churro.uinput;

import java.util.concurrent.atomic.AtomicReference;

import org.frc.team5409.churro.util.Vec2;

public final class IJoystick {
    private AtomicReference<Vec2> m_stick_axis;

    public IJoystick() {
        m_stick_axis = new AtomicReference<>(new Vec2(0,0));
    }

    public Vec2 getAxis() {
        return m_stick_axis.get();
    }

    public double getMagnitude() {
        return m_stick_axis.get().magnitude();
    }

    public double getTheta() {
        Vec2 axis = m_stick_axis.get();
        return Math.atan2(axis.y, axis.x);
    }

    protected void query(double x_axis, double y_axis) {
        m_stick_axis.set(new Vec2(x_axis, -y_axis)); // TODO: Find out if the y-axis needs to be flipped?
    }
}