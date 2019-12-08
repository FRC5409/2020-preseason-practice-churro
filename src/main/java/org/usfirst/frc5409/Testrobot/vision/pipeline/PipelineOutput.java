package org.usfirst.frc5409.Testrobot.vision.pipeline;

//import org.usfirst.frc5409.Testrobot.vision.exception.DataConversionException;

public abstract class PipelineOutput extends PipelineData {

    public PipelineInput asInput() {
        return this.asInput();
        //throw new DataConversionException(
        //    String.format("Attempted to convert %s to PipelineInput when no such conversion exists", this.getClass().getSimpleName()));
    };
}