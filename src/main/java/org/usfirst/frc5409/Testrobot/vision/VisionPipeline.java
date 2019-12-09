package org.usfirst.frc5409.Testrobot.vision;

import org.usfirst.frc5409.Testrobot.vision.pipeline.PipelineData;
import org.usfirst.frc5409.Testrobot.vision.structure.PipelineBranch;

public abstract class VisionPipeline<T extends PipelineData, R extends PipelineData> {
    public PipelineBranch main_branch;

    public VisionPipeline() {
        main_branch = new PipelineBranch();
    }

    public abstract T process(R input);
}