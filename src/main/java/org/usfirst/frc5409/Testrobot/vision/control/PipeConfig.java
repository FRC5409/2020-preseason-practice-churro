package org.usfirst.frc5409.Testrobot.vision.control;

public abstract class PipeConfig {
    public <T extends PipeConfig> T unpack() {
		return (T) (this);
    }
}