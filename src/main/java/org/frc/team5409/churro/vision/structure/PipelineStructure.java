package org.frc.team5409.churro.vision.structure;

public abstract class PipelineStructure {
    private boolean m_locked = false;

    public void finish() {
        m_locked = true;
    }

    protected final void lock() {
        m_locked = true;
    }

    protected final boolean isLocked() {
        return m_locked;
    }
}