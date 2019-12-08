package org.usfirst.frc5409.Testrobot.vision.pipeline;

//import org.usfirst.frc5409.Testrobot.vision.exception.DataConversionException;

public abstract class PipelineInput extends PipelineData {

    public PipelineOutput asOutput() {
        return super.asOutput();
        //throw new DataConversionException(
        //    String.format("Attempted to convert %s to PipelineOuptut when no such conversion exists", this.getClass().getSimpleName()));
    };
}