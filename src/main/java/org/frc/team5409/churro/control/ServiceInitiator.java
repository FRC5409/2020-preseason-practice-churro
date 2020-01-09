package org.frc.team5409.churro.control;


public abstract class ServiceInitiator {
    protected static final <T extends AbstractService> void addService(Class<T> service) {
        try {
            Class.forName(service.getName()); // Triggers static initializer
        } catch (Exception e) {
            // Should not throw
        }
    }
}