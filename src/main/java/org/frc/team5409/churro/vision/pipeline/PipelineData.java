package org.frc.team5409.churro.vision.pipeline;

import org.frc.team5409.churro.vision.exception.DataConversionException;

public abstract class PipelineData implements Cloneable {
    public PipelineData clone() { //TODO: This is so incredibly bad please this asap, absolutely disgusting and unsafe.
        try {
            return (PipelineData) super.clone();
        } catch (Exception e) {
            return null;
        }
    }  
    
    public <T extends PipelineData> T unpack() {
		return (T) (this);
    }

    public PipelineData pack() {
        return (PipelineData) (this);
    }

    public boolean asChoice() {
        throw new DataConversionException("Attempted to extract choice from Pipeline data with undefined conversion. Check code.");
    }
}