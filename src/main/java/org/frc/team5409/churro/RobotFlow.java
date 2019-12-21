package org.frc.team5409.churro;

import org.frc.team5409.churro.flow.FlowConfig;

public final class RobotFlow {
    private RobotFlow() {
    }

    public static FlowConfig configure() {
        FlowConfig config = new FlowConfig();
    //====================================================
        config.allocate(3);

        config.assign(
            "Human Drive Control [DCI]",
            5409,
            Double.class, Double.class
        );

        config.assign(
            "Vision Drive Control [DCI]",
            9540,
            Double.class, Double.class
        );

        config.assign(
            "Safety Drive Control [DCI]",
            9054,
            Double.class, Double.class
        );
    //====================================================
        return config;
    }
}