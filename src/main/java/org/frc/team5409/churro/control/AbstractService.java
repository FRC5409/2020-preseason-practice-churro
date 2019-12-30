package org.frc.team5409.churro.control;

import org.frc.team5409.churro.control.exception.CallSecurityException;

public abstract class AbstractService {
    protected static ServiceRegistrant ServiceRegistry;

    protected        ServiceBase       Service;
    protected        ServiceLogger     ServiceLogger;
    //protected        EventDispatcher ServiceDispatcher;

    static {
        ServiceRegistry = new ServiceRegistrant();
    }

    protected AbstractService() {
        if (!CallStack.checkFor(ServiceFactory.class))
            if (!CallStack.checkFor(ServiceBase.class)) // TODO: Figure out why constructor fires twice
                throw new CallSecurityException("Illegal construction of Service.");
    }

    protected abstract void init();

    protected void run() {
        // Override me !!!
    };

    public final String getName() {
        return Service.getName();
    }

    public final long getUID() {
        return Service.getUID();
    }
}