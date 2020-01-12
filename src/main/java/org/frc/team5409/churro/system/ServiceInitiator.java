package org.frc.team5409.churro.system;

import org.frc.team5409.churro.control.AbstractService;

public abstract class ServiceInitiator {
    protected static final void addService(Class<? extends AbstractService> service) {
        try {
            Class.forName(service.getName()); // Triggers static initializer
        } catch (Exception e) {
            // Should not throw
        }
    }
}