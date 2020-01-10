package org.frc.team5409.churro;

import org.frc.team5409.churro.control.ServiceInitiator;

import org.frc.team5409.churro.services.VisionService;

public final class RobotServices extends ServiceInitiator {
    public static void init() {
        addService(VisionService.class);
    }
}