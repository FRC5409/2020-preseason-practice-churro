package org.frc.team5409.churro.vision.structure;

import org.frc.team5409.churro.vision.exception.*;
import org.frc.team5409.churro.vision.pipeline.*;
import org.frc.team5409.churro.vision.control.*;

public class PipelineSwitch extends PipelineStructure implements PipelineStep {
    protected PipelineStep  m_step_off = null;
    protected PipelineStep  m_step_on = null;
    protected PipeSwitch    m_switch;

    public PipelineSwitch(PipeSwitch controller) {
        m_switch = controller;
    }

    public <T extends PipelineStep> PipelineSwitch add(T step, boolean switch_state) {
        if (isLocked())
            throw new LockedStructureException("Attempted to add steps to locked switch. Check code.");

        if (switch_state)
            m_step_on = step;
        else
            m_step_off = step;


        return this;
    }

    @Override
    public void finish() {
        if (m_step_on == null && m_step_off == null)
            throw new NoStepsException("Attempted to lock switch with no step cases.");
        
        lock();
    }

    @Override
    public PipelineData process(PipelineData input, PipeConfig config) {
        if (m_switch.get())
            return m_step_on.process(input, config);
        else
            return m_step_off.process(input, config);
    }
}