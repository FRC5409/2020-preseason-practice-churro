//Forward Kinematic Blind Tracking class
//Check README
package org.frc.team5409.churro.ada;
//This whole class is just terrible

import edu.wpi.first.wpilibj.Timer;
import org.frc.team5409.churro.util.Vector2;

public class FKBTrack {
    private double  m_wheel_diameter;
    private double  m_last_timestamp;

    private Vector2 m_fkbt_position;
    private Vector2 m_fkbt_normal;
    private double  m_fkbt_theta;

    private Object  m_this_lock;

    public FKBTrack(double w_d) {
        m_last_timestamp = Timer.getFPGATimestamp();
        m_wheel_diameter = w_d;
        
        m_fkbt_position = new Vector2(0,0);
        m_fkbt_normal = new Vector2(1, 0);
        m_fkbt_theta = 0.0d;

        m_this_lock = new Object();
    }

    public FKBTrack(double w_d, double r_x, double r_y, double r_t) {
        m_last_timestamp = Timer.getFPGATimestamp();
        m_wheel_diameter = w_d;
        
        m_fkbt_position = new Vector2(r_x, r_y);
        m_fkbt_normal = new Vector2(Math.cos(r_t), Math.sin(r_t));
        m_fkbt_theta = r_t;

        m_this_lock = new Object();
    }

    //Change this name asap
    public void reset(double r_x, double r_y, double r_t) {
        synchronized(m_this_lock) {
            m_last_timestamp = Timer.getFPGATimestamp();
            m_fkbt_position.x = r_x; //Not using new Vecotr2(x, y).
            m_fkbt_position.y = r_y; //Aren't constructors slower? need really fast

            m_fkbt_normal.x = Math.cos(r_t);
            m_fkbt_normal.y = Math.sin(r_t);
            m_fkbt_theta = r_t;
        }
    }

    //Nitty-Gritty math in here
    public void feed(double l_v, double r_v) {
        final double  w = (r_v - l_v) / m_wheel_diameter;
        final double  r = (l_v + r_v) / ((r_v - l_v)*2);
        final double  dt;
        
        final double icx, icy;     //Instantaneous Center of Curvature
        final double dw, dx, dy;   //Memoization purposes
        final double mx, my;       //Matrix stuffs
        
        synchronized(m_this_lock) {//Do not like this expsensive lock
            final double timestamp_now = Timer.getFPGATimestamp();
            dt = timestamp_now - m_last_timestamp;
            m_last_timestamp = timestamp_now;

            icx = m_fkbt_position.x - r * Math.sin(m_fkbt_theta);
            icy = m_fkbt_position.y + r * Math.cos(m_fkbt_theta);

            dw = w*dt;

            mx = Math.cos(dw);
            my = Math.sin(dw);

            dx = m_fkbt_position.x - icx;
            dy = m_fkbt_position.y - icy;

            //Apply this to new vehicle pose
            m_fkbt_position.x = mx*dx -my*dy + icx;
            m_fkbt_position.y = my*dx + mx*dy + icy;
            m_fkbt_theta += dw;

            //Calculate new normal
            m_fkbt_normal.x = Math.cos(m_fkbt_theta);
            m_fkbt_normal.y = Math.sin(m_fkbt_theta);
        }
    }


    public Vector2 getPosition() {
        Vector2 position;
        synchronized(m_this_lock) {
            position = m_fkbt_position;
        }

        return position;
    }

    public Vector2 getNormal() {
        Vector2 normal;
        synchronized(m_this_lock) {
            normal = m_fkbt_normal;
        }

        return normal;
    }

    public double getTheta() {
        double theta;
        synchronized(m_this_lock) {
            theta = m_fkbt_theta;
        }

        return theta;
    }
}