package org.usfirst.frc5409.Testrobot.vision.structure;

import org.usfirst.frc5409.Testrobot.vision.control.SwitchController;
import org.usfirst.frc5409.Testrobot.vision.exception.*;
import org.usfirst.frc5409.Testrobot.vision.pipeline.*;

public class PipelineSwitch extends PipelineStructure implements PipelineStep {
    protected SwitchController m_switch;
    protected PipelineStep     m_step_off = null;
    protected PipelineStep     m_step_on = null;

    public PipelineSwitch(SwitchController controller) {
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
    public PipelineOutput process(PipelineInput input) {
        if (m_switch.get())
            return m_step_on.process(input);
        else
            return m_step_off.process(input);
    }
}