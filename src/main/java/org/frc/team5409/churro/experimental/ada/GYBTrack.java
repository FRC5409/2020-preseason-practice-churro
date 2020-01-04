package org.frc.team5409.churro.experimental.ada;

import org.frc.team5409.churro.util.Vec2;

/**
 * Really crappy, rushed implementation of
 * gyroscopic tracking via navX. TODO: clean this up
 */
public class GYBTrack {
    private Vec2 m_gybt_pos; //Tracked position
    private Vec2 m_gybt_nmO; //Normal offset

    public GYBTrack() {
        m_gybt_pos = new Vec2();
        m_gybt_nmO = new Vec2();
    }

    public Vec2[] feed(double yaw, double vel) {
        final double y_pi = (yaw/180.0d)*Math.PI;
        final double mx = Math.cos(y_pi);
        final double my = Math.sin(y_pi);

        final Vec2 norm = new Vec2(
            (m_gybt_nmO.y*mx - m_gybt_nmO.x*my),
            (m_gybt_nmO.x*mx + m_gybt_nmO.y*my)
        ); 

        m_gybt_pos.x += norm.x * vel;
        m_gybt_pos.y += norm.y * vel;

        return new Vec2[] {m_gybt_pos, norm};
    }

    public void reset(double yaw) {
        final double y_pi = (yaw/180.0d)*Math.PI;

        m_gybt_pos = new Vec2();
        m_gybt_nmO = new Vec2(Math.cos(y_pi), Math.sin(y_pi));
    }

    public void reset(Vec2 normal) {
        m_gybt_pos = new Vec2();
        m_gybt_nmO = normal;
    }
}