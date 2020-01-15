package org.frc.team5409.churro.control;

import java.lang.reflect.Modifier;

import java.util.Hashtable;

import org.frc.team5409.churro.control.exception.*;

/**
 * <h2> ServiceManager </h2>
 * 
 * Facilitates and manages the execution of services
 * and provides an access point for services throughout
 * the robot program.
 * 
 * <p> This class serves as a registry and container for services. </p>

 */
public final class ServiceManager {
    private static ServiceManager              m_instance;

    private Hashtable<String, AbstractService> m_registry_name;
    private Hashtable<  Long, AbstractService> m_registry_uid;

    private        boolean                     m_srvcs_finalized;
    private        boolean                     m_srvcs_running;

    static {
        m_instance = new ServiceManager();
    }

    /**
     * Retrieves the ServiceManager singleton instance.
     * 
     * @return ServiceManager instance.
     */
    public static ServiceManager getInstance() {
        return m_instance;
    }

    private ServiceManager() {
        m_registry_name = new Hashtable<>();
        m_registry_uid = new Hashtable<>();

        m_srvcs_finalized = false;
        m_srvcs_running = false;
    }
    
    /**
     * Gets service with {@code name} from internal registry. 
     * If no service exists with {@code name},
     * an {@code IllegalServiceRequest} is thrown.
     * 
     * <pre>{@code
     *... 
     *    ExampleService inst;
     *    inst = ServiceManager.getService("ExampleService");
     *...
     *}</pre>
     * 
     * @param <T>  The service type.
     * 
     * @param name The name of the service.
     *
     * @return The requested service.
     * 
     * @throws IllegalServiceRequest Thrown when service with {@code name} does not exist.
     */
    @SuppressWarnings({ "unchecked" })
    public static <T extends AbstractService> T getService(String name) {
        T service_inst = (T) getInstance().m_registry_name.get(name);

        if (service_inst == null)
            throw new IllegalServiceRequest(String.format("Attempted to get unknown service \"%s\". Did you forget to register \"%s\"?", name, name));

        return service_inst;
    }

    /**
     * Gets service with {@code name} from internal registry. 
     * If no service exists with {@code name},
     * an {@code IllegalServiceRequest} is thrown.
     * 
     * <p> Inline version of {@link #getService(String)}. </p>
     * 
     * <pre>{@code
     *... 
     *    ServiceManager.getService("ExampleService", ExampleService.class).someMethod();
     *...
     *}</pre>
     * 
     * @param <V>     The service type.
     * 
     * @param name    The name of the service.
     * @param service The service class.
     *
     * @return The requested service.
     * 
     * @throws IllegalServiceRequest Thrown when service with {@code name} does not exist.
     */
    public static <V extends AbstractService> V getService(String name, Class<V> service) {
        return getService(name);
    }

    /**
     * Gets service with {@code uid} from internal registry. 
     * If no service exists with {@code uid},
     * an {@code IllegalServiceRequest} is thrown.
     * 
     * <pre>{@code
     *... 
     *    ExampleService inst;
     *    inst = ServiceManager.getService("ExampleService");
     *...
     *}</pre>
     * 
     * @param <T> The service type.
     * 
     * @param uid The uid of the service.
     *
     * @return The requested service.
     * 
     * @throws IllegalServiceRequest Thrown when service with {@code uid} does not exist.
     */
    @SuppressWarnings({ "unchecked" })
    public static <T extends AbstractService> T getService(long uid) {
        T service_inst = (T) getInstance().m_registry_uid.get(uid);

        if (service_inst == null)
            throw new IllegalServiceRequest(String.format("Attempted to get unknown service with uid \"%d\". Did you forget to register the service?", uid));

        return service_inst;
    }
    
    /**
     * Gets service with {@code uid} from internal registry. 
     * If no service exists with {@code uid},
     * an {@code IllegalServiceRequest} is thrown.
     * 
     * <p> Inline version of {@link #getService(Long)}. </p>
     * 
     * <pre>{@code
     *... 
     *    ServiceManager.getService("ExampleService", ExampleService.class).someMethod();
     *...
     *}</pre>
     * 
     * @param <V>     The service type.
     * 
     * @param uid     The uid of the service.
     * @param service The service class.
     *
     * @return The requested service.
     * 
     * @throws IllegalServiceRequest Thrown when service with {@code uid} does not exist.
     */
    public static <V extends AbstractService> V getService(long uid, Class<V> service) {
        return getService(uid);
    }

    /**
     * Registers service into the manager's internal registry
     * with {@code name} and {@code uid}.
     * 
     * <pre> {@code
     *
     *public final class ExampleService extends AbstractService { 
     *    static {
     *        ServiceRegistry.register("ExampleService", 5409L); // Proxy Method.
     *    }
     *}}</pre>
     *
     *
     * @param <T> The service type
     * 
     * @param name    The service name
     * @param uid     The service uid
     * @param service The service class
     * 
     * @throws IllegalServiceRequest   Thrown when the service isn't registered in {@code RobotServices}.
     * @throws InvalidServiceException Thrown when the service violates service implementation guidelines.
     */
    protected static <T extends AbstractService> void register(String name, long uid, Class<T> service) {
        try {
            registerService(name, uid, service);
        } catch (ControlException e) {
            e.printStackTrace();
        }
    }

    private static <T extends AbstractService> void registerService(String name, long uid, Class<T> service) {
        ServiceManager inst = getInstance();

        if (inst.m_srvcs_finalized)
            throw new IllegalServiceRequest("Illegal registration call after registry finalization. Did you forget to register the Service in RobotServices.java during program intialization?");
        else if (service.getDeclaredConstructors().length != 1)
            throw new InvalidServiceException("Illegal use of constructor in Service definition, use init() instead.");
        else if (service.getModifiers() != (Modifier.FINAL | Modifier.PUBLIC))
            throw new InvalidServiceException("Illegal modifiers in Service definition, must define service as 'public final' and CANNOT include 'abstract', 'static' or any other modifiers.");
        else if (service.getDeclaringClass() != null)
            throw new InvalidServiceException("Illegal Service defintion, must be a singleton top level class definition.");

        if (inst.m_registry_name.containsKey(name)) {
            AbstractService _inst = inst.m_registry_name.get(name);

            if (service.equals(_inst.getClass()))
                new InvalidServiceException("Illegal multiple registration calls from service definition.");
            else
                throw new InvalidServiceException(String.format("Service \"%s\" already exists with name %s.", _inst.getClass().getSimpleName(), name));
        } else if (inst.m_registry_uid.containsKey(uid)) {
            AbstractService _inst = inst.m_registry_uid.get(uid);

            if (service.equals(_inst.getClass()))
                throw new InvalidServiceException("Illegal multiple registration calls from service definition.");
            else
                throw new InvalidServiceException(String.format("Service \"%s\" already exists with UID %d.", _inst.getClass().getSimpleName(), uid));
        }
        
        T service_inst = ServiceFactory.create(name, uid, service);
            inst.m_registry_name.put(name, service_inst);
            inst.m_registry_uid.put(uid, service_inst);
    }

    /**
     * Initializes manager and all services.
     */
    public void initialize() {
        if (!CallStack.checkFor(org.frc.team5409.churro.Main.class))
            throw new CallSecurityException("Illegal initialization of Service Manager outside of main().");
        else if (m_srvcs_finalized)
            return;
        
        for (var entry : m_registry_name.entrySet()) {
            Exception e = callService(entry.getValue(), entry.getValue()::initialize);
            if (e != null)
                new ServiceExecutionException(String.format("Service \"%s\" encountered error during initialization.", entry.getKey()), e).printStackTrace();
        }
        m_srvcs_finalized = true;
    }

    /**
     * Starts all services.
     */
    public void startServices() {
        if (m_srvcs_running)
            return;

        for (var entry : m_registry_name.entrySet()) {
            Exception e = callService(entry.getValue(), entry.getValue()::start);
            if (e != null)
                new ServiceExecutionException(String.format("Service \"%s\" encountered error during start.", entry.getKey()), e).printStackTrace();
        }
        m_srvcs_running = true;
    }

    /**
     * Stops all services.
     */
    public void stopServices() {
        if (!m_srvcs_running)
            return;

        for (var entry : m_registry_name.entrySet()) {
            Exception e = callService(entry.getValue(), entry.getValue()::stop);
            if (e != null)
                new ServiceExecutionException(String.format("Service \"%s\" encountered error during stop.", entry.getKey()), e).printStackTrace();
            else
                entry.getValue().Service.alert(true);
        }
        m_srvcs_running = false;
    }

    private Exception callService(AbstractService service, Runnable service_call) {
        try {
            service_call.run();
            return null; // Call succeeded
        } catch (final Exception e) {                   
            m_registry_name.remove(service.getName());  // Call failed, therefore service impl. is faulty and
            m_registry_uid.remove(service.getUID());    // service will be removed from registry automatically

            return e;
        }
    }
}