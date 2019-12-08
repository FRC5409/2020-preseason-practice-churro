package org.usfirst.frc5409.Testrobot.vision.pipeline;

import org.usfirst.frc5409.Testrobot.vision.control.ChoiceData;
import org.usfirst.frc5409.Testrobot.vision.exception.DataConversionException;

public abstract class PipelineData {

    public PipelineInput asInput() {
        return (PipelineInput) (this);
    }

    public PipelineOutput asOutput() {
        return (PipelineOutput) (this);
    }

    public ChoiceData asChoice() {
        throw new DataConversionException("Attempted to extract choice from pipeline data with undefined conversion. Check code.");
    }
}