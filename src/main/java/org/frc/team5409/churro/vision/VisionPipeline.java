package org.frc.team5409.churro.vision;

import org.frc.team5409.churro.vision.pipeline.PipelineData;
import org.frc.team5409.churro.vision.structure.PipelineBranch;

public abstract class VisionPipeline<T extends PipelineData, R extends PipelineData> {
    protected PipelineBranch main_branch;

    public VisionPipeline() {
        main_branch = new PipelineBranch();
    }

    public abstract T process(R input);
}