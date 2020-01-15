package org.frc.team5409.churro.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.Spark;

public final class FeederControl implements Subsystem {
    private Spark  mo_pwm0_feeder_control;

    private double m_feeder_speed;

    public FeederControl() {
        mo_pwm0_feeder_control = new Spark(0);

        m_feeder_speed = 0;
    }

    
    public void runAt(double speed) {
        double clamped = clamp(-1,speed,1);

        m_feeder_speed = clamped;
        mo_pwm0_feeder_control.set(clamped);
    }

    public void stop() {
        mo_pwm0_feeder_control.setSpeed(0);
    }

    public double getSpeed() {
        return m_feeder_speed;
    }

    private double clamp(double mn, double v, double mx) {
        if (v > mx)
            return mx;
        else if (v < mn)
            return mn;
        return v;
    }
}