package org.frc.team5409.churro.services;

import java.util.concurrent.atomic.AtomicReference;

import edu.wpi.first.wpilibj.Timer;

import org.frc.team5409.churro.services.backend.vision.VisionBackend;
import org.frc.team5409.churro.vision.TargetType;
import org.frc.team5409.churro.control.AbstractService;
import org.frc.team5409.churro.control.EventEmitter;
import org.frc.team5409.churro.util.ErrorProfile;
import org.frc.team5409.churro.util.Vec3;


public final class VisionService extends AbstractService {
    static {
        ServiceRegistry.register("VisionService", 89162L);
    }

    private VisionBackend               m_backend;
    private AtomicReference<Vec3>       m_ll_track_data;
    private AtomicReference<TargetType> m_ll_target_data;

    private EventEmitter                m_onTargetAquired;
    private EventEmitter                m_onTargetLost;

    @Override
    protected void initialize() {
        m_backend         = new VisionBackend();
        m_ll_track_data   = new AtomicReference<>();
        m_ll_target_data  = new AtomicReference<>(TargetType.NONE);

        m_onTargetAquired = new EventEmitter();
        m_onTargetLost    = new EventEmitter();
    }

    @Override
    protected void start() {
        ServiceRunner.runThread(this::run);
    }

    @Override
    protected void stop() {
        ServiceRunner.stopThread();
    }

    @Override
    protected void run() {
        if (m_backend.isTargeted()) {
            aquireTarget(true);
            while (m_backend.isTargeted()) {
                if (!m_err_profile.isAcceptable())
                    break;
                updateTarget();
            }
            looseTarget(true);
        }
    }

    public boolean hasTarget() {
        return m_ll_target_data.get() != TargetType.NONE;
    }

    public Vec3 getTarget() {
        return m_ll_track_data.get();
    }

    public TargetType getTargetType() {
        return m_ll_target_data.get();
    }

    private boolean aquireTarget(boolean do_emit) {
        m_err_profile.reset();
        while (m_backend.isTargeted()) {
            if (m_err_profile.isAcceptable()) {
                TargetType type = m_backend.getTargetType();
                m_ll_target_data.set(type);
                if (do_emit)
                    emit(m_onTargetAquired, type, updateTarget());
                return true;
            }
        }

        return false;
    }

    private void looseTarget(boolean do_emit) {
        m_ll_target_data.set(TargetType.NONE);
        if (do_emit)
            emit(m_onTargetLost);
    }

    private Vec3 updateTarget() {
        Vec3 target = m_backend.getTarget();
        m_ll_track_data.set(target);
        return target;
    }

    private synchronized void emit(EventEmitter emitter, Object... args) {
        emitter.emit(args);
    }


    private ErrorProfile m_err_profile = new ErrorProfile() {
        private final double max_latency_ms = 25; //ms
        private final double min_targeted_sec = 0.5; //sec
        private final double max_profile_err = 0.781; //Random constants

        private double m_last_ts;

        @Override
        public void reset() {
            setError(0);
            m_last_ts = Timer.getFPGATimestamp();
        }

        @Override
        public void profile() {
            setError(m_backend.getLatency() / max_latency_ms * validity());
        }

        private double validity() {
            if (Timer.getFPGATimestamp() - m_last_ts > min_targeted_sec)
                return 1.0d;
            else
                return -1.0d;
        }
    };
}