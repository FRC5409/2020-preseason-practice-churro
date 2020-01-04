package org.frc.team5409.churro.services;

import org.frc.team5409.churro.control.AbstractService;

public final class RobotIdentityService extends AbstractService {
    public final static long  serviceUID = 97612L;

    static {
        ServiceRegistry.register("VisionService");
    }

    @Override
    protected void init() {

        SERunner.run(this::run);
    }


    @Override
    protected void run() {
        while (!SERunner.interrupted()) {

        }
    }
}