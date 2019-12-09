package org.usfirst.frc5409.Testrobot.vision.pipeline;

import org.usfirst.frc5409.Testrobot.vision.control.PipeConfig;

@FunctionalInterface
public interface PipelineStep <T extends PipelineData, R extends PipeConfig, V extends PipelineData> {
    public V process(T input, R config);
}