package org.frc.team5409.churro.vision.control;

public abstract class PipeConfig {
    public <T extends PipeConfig> T unpack() {
		return (T) (this);
    }
}