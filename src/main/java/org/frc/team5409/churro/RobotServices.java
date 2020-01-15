package org.frc.team5409.churro;

import org.frc.team5409.churro.system.ServiceInitiator;

import org.frc.team5409.churro.services.RobotService;
import org.frc.team5409.churro.services.UserInputService;
import org.frc.team5409.churro.services.VisionService;

public final class RobotServices extends ServiceInitiator {
    protected static void register() {
        addService(VisionService.class);
        addService(RobotService.class);
        addService(UserInputService.class);
    }
}