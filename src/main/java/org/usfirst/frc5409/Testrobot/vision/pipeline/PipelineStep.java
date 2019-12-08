package org.usfirst.frc5409.Testrobot.vision.pipeline;

public interface PipelineStep<T extends PipelineInput, O extends PipelineOutput> {
    public O process(T input);
}