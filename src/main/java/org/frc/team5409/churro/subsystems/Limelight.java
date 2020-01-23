package org.frc.team5409.churro.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.*;

import org.frc.team5409.churro.limelight.*;
import org.frc.team5409.churro.util.Vec2;

/**
 * Limelight subsystem.
 * 
 * Facilitates the control and access
 * of limelight hardware through a software
 * interface.
 */
public class Limelight extends SubsystemBase {
    private NetworkTable         m_limelight_data;

    private NetworkTableEntry    m_data_entry_tx;
    private NetworkTableEntry    m_data_entry_ty;
    private NetworkTableEntry    m_data_entry_ta;
    private NetworkTableEntry    m_data_entry_getpipe;
    private NetworkTableEntry    m_data_entry_led_mode;
    private NetworkTableEntry    m_data_entry_cam_mode;
    private NetworkTableEntry    m_data_entry_pipeline;
    private NetworkTableEntry    m_data_entry_cam_track;
    private NetworkTableEntry    m_data_entry_has_targets;

    private LedMode              m_local_led_mode;
    private CameraMode           m_local_cam_mode;
    private PipelineIndex        m_local_pipeline;

    private Object               m_this_mutex;

    private double[]             NO_TRACK = new double[6];

    /**
     * Construct subsystem and initialize limeight communication
     */
    public Limelight() {
        // Maybe create a watchdog system in case of limelight malfunction (i.e disconnection)
        m_limelight_data         = NetworkTableInstance.getDefault().getTable("limelight");

        m_data_entry_tx          = m_limelight_data.getEntry("tx");
        m_data_entry_ty          = m_limelight_data.getEntry("ty");
        m_data_entry_ta          = m_limelight_data.getEntry("ta");
        m_data_entry_getpipe     = m_limelight_data.getEntry("getpipe");
        m_data_entry_cam_mode    = m_limelight_data.getEntry("camMode");
        m_data_entry_led_mode    = m_limelight_data.getEntry("ledMode");
        m_data_entry_pipeline    = m_limelight_data.getEntry("pipeline");
        m_data_entry_cam_track   = m_limelight_data.getEntry("camtran");
        m_data_entry_has_targets = m_limelight_data.getEntry("tv");

        m_local_led_mode         = LedMode.LED_OFF;
        m_local_cam_mode         = CameraMode.MODE_DRIVER;
        m_local_pipeline         = PipelineIndex.PIPELINE_0;

        m_this_mutex = new Object();
    }
    
    @Override
    public void periodic() {
        double tx[] = m_limelight_data.getEntry("tcornx").getDoubleArray(new double[1]);
        double ty[] = m_limelight_data.getEntry("tcorny").getDoubleArray(new double[1]);
    
        SmartDashboard.putNumberArray("tcornx", tx);
        SmartDashboard.putNumberArray("tcorny", ty);
    }

    /**
     * Set Camera Mode on limelight
     * 
     * @param camera_mode Camera Mode
     * 
     * @see CameraMode
     */
    public void setCameraMode(CameraMode camera_mode) {
        final double camera_mode_byte = camera_mode.get();

        synchronized(m_this_mutex) {
            m_data_entry_cam_mode.setDouble(camera_mode_byte);
            m_local_cam_mode = camera_mode;
        }
    }

    /**
     * Get current Camera Mode on limelight
     * 
     * @return Current Camera Mode
     * 
     * @see CameraMode
     */
    public CameraMode getCameraMode() {
        final double real_camera_mode = m_data_entry_cam_mode.getDouble(-1);

        if (real_camera_mode == -1) {
            //Do something here, this is probably an error
        }

        synchronized(m_this_mutex) {
            if (m_local_cam_mode.get() != real_camera_mode)
                m_data_entry_cam_mode.forceSetDouble(m_local_cam_mode.get());
        }
        return m_local_cam_mode;
    }

    /**
     * Set Led Mode on limelight
     * 
     * @param led_mode Led Mode
     * 
     * @see LedMode
     */
    public void setLedMode(LedMode led_mode) {
        final double led_mode_byte = led_mode.get();

        synchronized(m_this_mutex) {
            m_data_entry_led_mode.setDouble(led_mode_byte);
            m_local_led_mode = led_mode;
        }
    }

    /**
     * Get current Led Mode on limelight
     * 
     * @return Current Led Mode
     * 
     * @see LedMode
     */
    public LedMode getLedMode() {
        final double real_led_mode = m_data_entry_led_mode.getDouble(-1);

        if (real_led_mode == -1) {
            //Do something here, this is probably an error
        }

        synchronized(m_this_mutex) {
            if (m_local_led_mode.get() != real_led_mode) //Is this check redundant?
                m_data_entry_led_mode.forceSetDouble(m_local_led_mode.get());
        }
        return m_local_led_mode;
    }

    /**
     * Set Pipeline Index on limelight
     * 
     * @param pipeline_index Pipeline Index
     * 
     * @see PipelineIndex
     */
    public void setPipelineIndex(PipelineIndex pipeline_index) {
        final double pipeline_index_num = pipeline_index.get();

        synchronized(m_this_mutex) {
            m_data_entry_pipeline.setDouble(pipeline_index_num);
            m_local_pipeline = pipeline_index;
        }
    }

    /**
     * Get current Pipeline Index on limelight
     * 
     * @return Current Pipeline Index
     * 
     * @see PipelineIndex
     */
    public PipelineIndex getPipelineIndex() {
        final double real_pipeline_index = m_data_entry_getpipe.getDouble(-1);

        if (real_pipeline_index == -1) {
            //Do something here, this is probably an error
        }

        synchronized(m_this_mutex) {
            if (m_local_pipeline.get() != real_pipeline_index)
                m_data_entry_cam_mode.forceSetDouble(m_local_pipeline.get());
        }
        return m_local_pipeline;
    }

    /**
     * Get current Camera Track from PNP Pipeline.
     * 
     * @return Camera Tracking Matrix
     */
    public TrackMatrix getCameraTrack() {
        double[] raw_cam_matrix = new double[6];

        synchronized(m_this_mutex) {
            raw_cam_matrix = m_data_entry_cam_track.getDoubleArray(NO_TRACK);
        }

        checkTrack: {
            for (int i=0; i<6; i++) {
                if (raw_cam_matrix[i] != 0.0d)
                    break checkTrack;
            }

            return null;//No tracking data recorded
        }

        return new TrackMatrix(raw_cam_matrix[0], raw_cam_matrix[1], raw_cam_matrix[2],
                                  raw_cam_matrix[3], raw_cam_matrix[4], raw_cam_matrix[5]);
    }

    /**
     * Get current Tracking Target from Limelight Pipeline.
     * 
     * @return Limelight Pipeline
     */
    public Vec2 getTarget() {
        Vec2 target = new Vec2();

        synchronized(m_this_mutex) {
            target.x = m_data_entry_tx.getDouble(target.x);
            target.y = m_data_entry_ty.getDouble(target.y);
        }
        
        return target;
    }

    /**
     * 
     */
    public double getTargetArea() {
        double raw_cam_ta;

        synchronized(m_this_mutex) {
            raw_cam_ta = m_data_entry_ta.getDouble(0);
        }

        return raw_cam_ta;
    }

    /**
     * Checks to see if the limelight is currently tracking any targets.
     * 
     * @return Whether or not the limelight is tracking a target.
     */
    public boolean hasTarget() {
        double has_target;
        
        synchronized(m_this_mutex) {
            has_target = m_data_entry_has_targets.getDouble(0);
        }

        return has_target==1;
    }
}