package org.frc.team5409.churro.vision.pipeline;

import org.frc.team5409.churro.vision.control.PipeConfig;

@FunctionalInterface
public interface PipelineStep <T extends PipelineData, R extends PipeConfig, V extends PipelineData> {
    public V process(T input, R config);
}