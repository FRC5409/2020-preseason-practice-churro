package org.usfirst.frc5409.Testrobot.vision.structure;

import java.util.Arrays;

import org.usfirst.frc5409.Testrobot.vision.exception.*;
import org.usfirst.frc5409.Testrobot.vision.pipeline.*;
import org.usfirst.frc5409.Testrobot.vision.control.*;

public class PipelineBranch extends PipelineStructure implements PipelineStep {
    protected PipelineStep m_steps[];
    protected int          m_steps_length;

    public PipelineBranch() {
        m_steps        = new PipelineStep[1];
        m_steps_length = 0;
    }

    public PipelineBranch(int num_steps) {
        m_steps        = new PipelineStep[num_steps];
        m_steps_length = 0;
    }

    public final <T extends PipelineStep> PipelineBranch add(T step) {
        if (isLocked())
            throw new LockedStructureException("Attempted to add steps to locked branch. Check code. (Safety measure)");

        m_steps_length++;

        if (m_steps_length > m_steps.length)
            m_steps = Arrays.copyOf(m_steps, m_steps_length);

        m_steps[m_steps_length-1] = step;
        

        return this;
    }

    public final void finish() {
        if (m_steps_length == 0) 
            throw new NoStepsException("Attempted to lock branch with no steps.");
        
        lock();
    }

    @Override
    public PipelineData process(PipelineData input, PipeConfig config) {
        PipelineData data = input.clone();
        
        if (m_steps_length == 0) //TODO: Might not need this considering comparison cost at every iteration vs Safety
            throw new NoStepsException("Attempted to run branch with no steps.");

        for (int i=0; i < m_steps_length; i++) {
            data = m_steps[i].process(data, config);
        }

        return data;
    }
}