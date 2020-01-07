package org.frc.team5409.churro.services;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.wpi.first.wpilibj.Timer;

import org.frc.team5409.churro.services.impl.vision.VisionBackend;
import org.frc.team5409.churro.services.vision.TargetType;
import org.frc.team5409.churro.control.AbstractService;
import org.frc.team5409.churro.control.EventEmitter;
import org.frc.team5409.churro.util.ErrorProfile;
import org.frc.team5409.churro.util.Vec3;


public final class VisionService extends AbstractService {
    public final static long serviceUID = 89162L;

    private final static class config {
        public static final double max_latency_ms = 25; //ms
        public static final double min_targeted_sec = 0.5; //sec
        public static final double max_profile_err = 0.781; //Random constants
    }

    private VisionBackend               m_backend;
    private AtomicBoolean               m_ll_is_targeted;
    private AtomicReference<Vec3>       m_ll_track_data;
    private AtomicReference<TargetType> m_ll_target_data;

    private EventEmitter                m_onTargetAquired;
    private EventEmitter                m_onTargetLost;

    static {
        ServiceRegistry.register("VisionService");
    }

    @Override
    protected void init() {
        m_backend         = new VisionBackend();
        m_ll_is_targeted  = new AtomicBoolean(false);
        m_ll_track_data   = new AtomicReference<>();
        m_ll_target_data  = new AtomicReference<>(TargetType.NONE);

        m_onTargetAquired = new EventEmitter();
        m_onTargetLost    = new EventEmitter();
        
        m_ll_profile.setMaxError(config.max_profile_err);

        SERunner.run(this::run);
    }

    @Override
    protected void run() {
        while (!SERunner.interrupted()) {
            if (m_backend.isTargeted()) {
                aquireTarget();
                while (m_backend.isTargeted()) {
                    if (!m_ll_profile.isAcceptable())
                        break;
                    updateTarget();
                }
                emit(m_onTargetLost);
            }
        }
    }

    public Vec3 getTarget() {
        return m_ll_track_data.get();
    }

    public TargetType getTargetType() {
        return m_ll_target_data.get();
    }

    private boolean aquireTarget() {
        m_ll_profile.reset();
        while (m_backend.isTargeted()) {
            if (m_ll_profile.isAcceptable()) {
                TargetType type = m_backend.getTargetType();

                m_ll_target_data.set(type);
                emit(m_onTargetAquired, type, updateTarget());
                return true;
            }
        }

        return false;
    }

    private Vec3 updateTarget() {
        final Vec3 target = m_backend.getTarget();
        m_ll_track_data.set(target);
        return target;
    }

    private synchronized void emit(EventEmitter emitter, Object... args) {
        emitter.emit(args);
    }

    private ErrorProfile m_ll_profile = new ErrorProfile() {
        private double m_last_ts;

        @Override
        public void reset() {
            setError(0);
            m_last_ts = Timer.getFPGATimestamp();
        }

        @Override
        public void profile() {
            setError(m_backend.getLatency() / config.max_latency_ms * validity());
        }

        private double validity() {
            if (Timer.getFPGATimestamp() - m_last_ts > config.min_targeted_sec)
                return 1.0d;
            else
                return -1.0d;
        }
    }; 
}