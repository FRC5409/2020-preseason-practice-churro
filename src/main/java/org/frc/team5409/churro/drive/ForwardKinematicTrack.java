package org.frc.team5409.churro.drive;

import edu.wpi.first.wpilibj.Timer;

import org.frc.team5409.churro.util.Vec2;

public final class ForwardKinematicTrack {
    private final double m_fkt_wheel_dist;
    
    private       Vec2   m_fkt_position;
    private       Vec2   m_fkt_direction;
    private       double m_fkt_theta;
    private       double m_fkt_last_feed;

    public ForwardKinematicTrack(double fkt_wheel_dist) {
        m_fkt_wheel_dist = fkt_wheel_dist;
        m_fkt_last_feed = Timer.getFPGATimestamp();
    }

    public void feed(double left_velocity, double right_velocity) {
        final double icx, icy;        //Instantaneous Center of Curvature
        final double dw, dx, dy, dt;  //Memoization purposes
        final double mx, my;          //Matrix stuffs
        final double w, r;

        w = (right_velocity - left_velocity) / m_fkt_wheel_dist;
        r = (left_velocity + right_velocity) / ((right_velocity - left_velocity)*2);

        dt = Timer.getFPGATimestamp() - m_fkt_last_feed;
        m_fkt_last_feed = Timer.getFPGATimestamp();

        icx = m_fkt_position.x - r * Math.sin(m_fkt_theta);
        icy = m_fkt_position.y + r * Math.cos(m_fkt_theta);

        dw = w*dt;

        mx = Math.cos(dw);
        my = Math.sin(dw);

        dx = m_fkt_position.x - icx;
        dy = m_fkt_position.y - icy;

        //Apply this to new vehicle pose
        m_fkt_position.x = mx*dx -my*dy + icx;
        m_fkt_position.y = my*dx + mx*dy + icy;
        m_fkt_theta += dw;

        //Calculate new normal
        m_fkt_direction.x = Math.cos(m_fkt_theta);
        m_fkt_direction.y = Math.sin(m_fkt_theta);
    }

    public void reset() {
        m_fkt_last_feed = Timer.getFPGATimestamp();
        m_fkt_position = new Vec2(0,0);
        m_fkt_direction = new Vec2(0,0);
        m_fkt_theta = 0;
    }

    public void setPosition(Vec2 fkt_position) {
        m_fkt_position = fkt_position;
    }

    public void setDirection(Vec2 fkt_direction) {
        m_fkt_direction = fkt_direction.normalize();
    }

    public Vec2 getPosition() {
        return m_fkt_position;
    }

    public Vec2 getDirection() {
        return m_fkt_direction;
    }

    public double getTheta() {
        return m_fkt_theta;
    }
}