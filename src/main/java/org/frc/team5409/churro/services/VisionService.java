package org.frc.team5409.churro.services;

import org.frc.team5409.churro.control.AbstractService;
import org.frc.team5409.churro.control.EventEmitter;
import org.frc.team5409.churro.limelight.*;
import org.frc.team5409.churro.util.Vec2;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public final class VisionService extends AbstractService {
    public final static long  serviceUID = 89162L;

    private EventEmitter      m_em_onTargetAquired;
    private EventEmitter      m_em_onTargetLost;

    private NetworkTable      m_limelight_data;

    private NetworkTableEntry m_data_entry_tx;
    private NetworkTableEntry m_data_entry_ty;
    private NetworkTableEntry m_data_entry_ta;
    private NetworkTableEntry m_data_entry_getpipe;
    private NetworkTableEntry m_data_entry_led_mode;
    private NetworkTableEntry m_data_entry_cam_mode;
    private NetworkTableEntry m_data_entry_pipeline;
    private NetworkTableEntry m_data_entry_cam_track;
    private NetworkTableEntry m_data_entry_has_targets;

    private LedMode           m_local_led_mode;
    private CameraMode        m_local_cam_mode;
    private PipelineIndex     m_local_pipeline;

    static {
        ServiceRegistry.register("VisionService");
    }

    @Override
    protected void init() {
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
        m_local_cam_mode         = CameraMode.MODE_VISION;
        m_local_pipeline         = PipelineIndex.PIPELINE_0;


        SERunner.run(this::run);
    }


    @Override
    protected void run() {
        while (!SERunner.interrupted()) {

        }
    }

    public Vec2 getTarget() {
        return new Vec2(10, 0);
    }
}