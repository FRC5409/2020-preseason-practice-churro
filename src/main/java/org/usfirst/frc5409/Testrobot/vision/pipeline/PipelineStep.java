package org.usfirst.frc5409.Testrobot.vision.pipeline;

@FunctionalInterface
public interface PipelineStep <T extends PipelineData, V extends PipelineData> {
    public V process(T input);
}