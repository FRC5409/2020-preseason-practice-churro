package org.frc.team5409.churro.control;

import org.frc.team5409.churro.control.exception.CallSecurityException;

/**
 * <h2> AbstractService </h2>
 * 
 * A globally accessible system based on the processing,
 * distribution and abstraction of information.
 * 
 * <p> This system can be accessed and utilized anywhere in
 * the program, and should be designed to function in such
 * an environment. </p>
 * 
 * A service receives information from several sources,
 * such as a {@code ServiceBackend}, other {@code Services}
 * as well as internal processes.
 * 
 * <p> Services must be implemented to handle multiple
 * calls from different threads at any time. They must be
 * thread-safe, offer synchronization, and avoid race conditions. </p>
 */
public abstract class AbstractService {
    protected static ServiceRegistrant ServiceRegistry;

    protected        ServiceBase       Service;
    protected        ServiceLogger     SELogger;
    protected        ServiceRunner     SERunner;

    static {
        ServiceRegistry = new ServiceRegistrant();
    }

    protected AbstractService() {
        if (!CallStack.checkFor(ServiceFactory.class))
            if (!CallStack.checkFor(AbstractService.class)) // TODO: Figure out why constructor fires twice
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