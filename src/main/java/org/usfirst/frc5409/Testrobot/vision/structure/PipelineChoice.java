package org.usfirst.frc5409.Testrobot.vision.structure;

import org.usfirst.frc5409.Testrobot.vision.exception.*;
import org.usfirst.frc5409.Testrobot.vision.pipeline.*;

public class PipelineChoice extends PipelineStructure implements PipelineStep {
    protected PipelineStep m_step_off = null;
    protected PipelineStep m_step_on = null;

    public <T extends PipelineStep> PipelineChoice add(T step, boolean switch_state) {
        if (isLocked())
            throw new LockedStructureException("Attempted to add steps to locked choice. Check code.");

        if (switch_state)
            m_step_on = step;
        else
            m_step_off = step;

        return this;
    }

    @Override
    public void finish() {
        if (m_step_on == null && m_step_off == null)
            throw new NoStepsException("Attempted to lock choice with no step cases.");
        
        lock();
    }

    @Override
    public PipelineData process(PipelineData input) {
        if (input.asChoice())
            return m_step_on.process(input);
        else
            return m_step_off.process(input);
    }
}