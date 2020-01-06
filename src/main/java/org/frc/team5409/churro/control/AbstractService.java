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
    /** 
     * <h3> Service Registration utility. </h3>
     * 
     * Provides an interface between the {@code ServiceManager} and
     * the {@code AbstractService} and facilitates the registration
     * of Services.
     */
    protected static ServiceRegistrant ServiceRegistry;
    
    /** 
     * <h3> Service information. </h3>
     * 
     * Provides base information of the Service, such as
     * it's name, uid, etc.
     */
    protected        ServiceBase       Service;

    /** 
     * <h3> Service Logging utility. </h3>
     * 
     * Provides an abstraction of console logging
     * functionality.
     */
    protected        ServiceLogger     SELogger;

    /** 
     * <h3> Service Runner utility. </h3>
     * 
     * Provides a functional wrapper for managing
     * threads relating to services.
     */
    protected        ServiceRunner     SERunner;

    static {
        ServiceRegistry = new ServiceRegistrant();
    }

    protected AbstractService() {
        if (!CallStack.checkFor(ServiceFactory.class))
            if (!CallStack.checkFor(AbstractService.class)) // TODO: Figure out why constructor fires twice
                throw new CallSecurityException("Illegal construction of Service.");
    }

    /**
     * Initializes a Service.
     */
    protected abstract void init();

    /**
     * Default Service thread function.
     */
    protected void run() {
        // Override me !!!
    };

    /**
     * Retrieves Service name.
     */
    public final String getName() {
        return Service.getName();
    }

    /**
     * Retrieves Service uid.
     */
    public final long getUID() {
        return Service.getUID();
    }
}