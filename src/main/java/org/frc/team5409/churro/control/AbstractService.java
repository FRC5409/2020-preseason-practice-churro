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
 * 
 * @author Keith Davies
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
     * functionality for service utilization.
     */
    protected        ServiceLogger     ServiceLogger;

    /** 
     * <h3> Service Runner utility. </h3>
     * 
     * Provides a functional wrapper for managing
     * threads relating to service activities.
     */
    protected        ServiceRunner     ServiceRunner;

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
    protected abstract void initialize();

    /**
     * Starts a Service.
     */
    protected abstract void start();

    /**
     * Stops a Service
     */
    protected abstract void stop();

    /**
     * Default Service thread method.
     */
    protected void run() {
        // Override me !!!
    };

    /**
     * Get Service name.
     */
    public final String getName() {
        return Service.getName();
    }

    /**
     * Get Service uid.
     */
    public final long getUID() {
        return Service.getUID();
    }
}