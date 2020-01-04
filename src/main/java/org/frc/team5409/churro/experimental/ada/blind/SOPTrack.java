package org.frc.team5409.churro.experimental.ada.blind;

import org.frc.team5409.churro.util.Vec2;

import edu.wpi.first.wpilibj.Timer;

public class SOPTrack {
    //public double    sop_trg_height;
    //public double    sop_org_height;
    //public double    sop_org_angle;

    private double   var_last_time;
    private double   var_last0_angle;
    private double   var_last1_angle;
    private double   var_angle_vel;


    private boolean  m_stop_track;
    private boolean  m_start_track;

    public SOPTrack(/*double trg_height, double org_height, double org_angle*/) {
        //sop_trg_height = trg_height;
        //sop_org_height = org_height;
        //sop_org_angle  = org_angle;

        var_angle_vel  = 0;
        m_stop_track   = false;
        m_start_track  = false;
        var_last_time  = Timer.getFPGATimestamp();
        var_last0_angle = 0;
        var_last1_angle = 0;
    }

    public void initiate() {
        //m_start_track = true;
        final double current_time = Timer.getFPGATimestamp();
        var_angle_vel = (var_last0_angle - var_last1_angle) / (current_time - var_last_time);
    }

    public void deInitiate() {
        //m_stop_track = true;
        var_angle_vel = 0;
    }

    public void feed(double in_lens_angle) {
        var_last_time = Timer.getFPGATimestamp();

        var_last1_angle = var_last0_angle;
        var_last0_angle = in_lens_angle;

    }

    public double track() {
        return (Timer.getFPGATimestamp() - var_last_time) * var_angle_vel;
    }
}