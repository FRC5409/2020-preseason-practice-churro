package org.frc.team5409.churro.services.backend.vision;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;

import org.frc.team5409.churro.vision.TargetType;
import org.frc.team5409.churro.limelight.*;
import org.frc.team5409.churro.util.Vec3;

public final class VisionBackend {
    private NetworkTable      m_ll_data;

    private NetworkTableEntry m_ll_entry_tx;
    private NetworkTableEntry m_ll_entry_ty;
    private NetworkTableEntry m_ll_entry_ta;
    private NetworkTableEntry m_ll_entry_led_mode;
    private NetworkTableEntry m_ll_entry_cam_mode;
    private NetworkTableEntry m_ll_entry_pipeline;
    private NetworkTableEntry m_ll_entry_is_targeted;
    private NetworkTableEntry m_ll_entry_latency;

    private LedMode           m_local_led_mode;
    private CameraMode        m_local_cam_mode;
    private PipelineIndex     m_local_pipeline;

    public VisionBackend() {
        m_ll_data              = NetworkTableInstance.getDefault().getTable("limelight");

        m_ll_entry_tx          = m_ll_data.getEntry("tx");
        m_ll_entry_ty          = m_ll_data.getEntry("ty");
        m_ll_entry_ta          = m_ll_data.getEntry("ta");
        m_ll_entry_cam_mode    = m_ll_data.getEntry("camMode");
        m_ll_entry_led_mode    = m_ll_data.getEntry("ledMode");
        m_ll_entry_pipeline    = m_ll_data.getEntry("pipeline");
        m_ll_entry_is_targeted = m_ll_data.getEntry("tv");
        m_ll_entry_latency     = m_ll_data.getEntry("tl");

        m_local_led_mode       = LedMode.LED_OFF;
        m_local_cam_mode       = CameraMode.MODE_VISION;
        m_local_pipeline       = PipelineIndex.PIPELINE_0;
    }

    public void setLedMode(LedMode mode) {
        m_local_led_mode = mode;

        m_ll_entry_led_mode.setDouble(mode.get());
    }

    public void setCameraMode(CameraMode mode) {
        m_local_cam_mode = mode;

        m_ll_entry_cam_mode.setDouble(mode.get());
    }

    public void setPipelineIndex(PipelineIndex pipeline) {
        m_local_pipeline = pipeline;

        m_ll_entry_pipeline.setDouble(pipeline.get());
    }

    public boolean isTargeted() {
        return m_ll_entry_is_targeted.getDouble(0) != 0;
    }

    public Vec3 getTarget() {
        return new Vec3(
            m_ll_entry_tx.getDouble(0),
            m_ll_entry_ty.getDouble(0),
            m_ll_entry_ta.getDouble(0)
        );
    }

    public TargetType getTargetType() {
        return (isTargeted()) ? TargetType.LOWER_PORT : TargetType.NONE;
    }

    public double getLatency() {
        return m_ll_entry_latency.getDouble(11);
    }

    public LedMode getLedMode() {
        return m_local_led_mode;
    }

    public CameraMode getCameraMode() {
        return m_local_cam_mode;
    }

    public PipelineIndex getPipelineIndex() {
        return m_local_pipeline;
    }
}