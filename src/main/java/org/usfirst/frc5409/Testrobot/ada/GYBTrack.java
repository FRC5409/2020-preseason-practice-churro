package org.usfirst.frc5409.Testrobot.ada;

import org.usfirst.frc5409.Testrobot.util.Vector2;

/**
 * Really crappy, rushed implementation of
 * gyroscopic tracking via navX. TODO: clean this up
 */
public class GYBTrack {
    private Vector2 m_gybt_pos; //Tracked position
    private Vector2 m_gybt_nmO; //Normal offset

    public GYBTrack() {
        m_gybt_pos = new Vector2();
        m_gybt_nmO = new Vector2();
    }

    public Vector2[] feed(double yaw, double vel) {
        final double y_pi = (yaw/180.0d)*Math.PI;
        final double mx = Math.cos(y_pi);
        final double my = Math.sin(y_pi);

        final Vector2 norm = new Vector2(
            (m_gybt_nmO.y*mx - m_gybt_nmO.x*my),
            (m_gybt_nmO.x*mx + m_gybt_nmO.y*my)
        ); 

        m_gybt_pos.x += norm.x * vel;
        m_gybt_pos.y += norm.y * vel;

        return new Vector2[] {m_gybt_pos, norm};
    }

    public void reset(double yaw) {
        final double y_pi = (yaw/180.0d)*Math.PI;

        m_gybt_pos = new Vector2();
        m_gybt_nmO = new Vector2(Math.cos(y_pi), Math.sin(y_pi));
    }

    public void reset(Vector2 normal) {
        m_gybt_pos = new Vector2();
        m_gybt_nmO = normal;
    }
}