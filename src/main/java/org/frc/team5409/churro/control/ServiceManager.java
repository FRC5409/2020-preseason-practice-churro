package org.frc.team5409.churro.control;

import java.lang.reflect.Modifier;

import java.util.Hashtable;

import org.frc.team5409.churro.control.exception.*;

public final class ServiceManager {
    private static ServiceManager              m_instance;

    private Hashtable<String, AbstractService> m_registry_name;
    private Hashtable<  Long, AbstractService> m_registry_uid;

    private        boolean                     m_finalized;
    private        boolean                     m_running;

    static {
        m_instance = new ServiceManager();
    }

    public static ServiceManager getInstance() {
        return m_instance;
    }

    private ServiceManager() {
        m_registry_name = new Hashtable<>();
        m_registry_uid = new Hashtable<>();

        m_finalized = false;
        m_running = false;
    }
    
    @SuppressWarnings({ "unchecked" })
    public static <T extends AbstractService> T getService(String name) {
        T service_inst = (T) getInstance().m_registry_name.get(name);

        if (service_inst == null)
            throw new IllegalServiceRequest(String.format("Attempted to get unknown service \"%s\". Did you for get to register \"%s\"?", name, name));

        return service_inst;
    }

    @SuppressWarnings({ "unchecked" })
    public static <T extends AbstractService> T getService(long uid) {
        T service_inst = (T) getInstance().m_registry_uid.get(uid);

        if (service_inst == null)
            throw new IllegalServiceRequest(String.format("Attempted to get unknown service with uid \"%d\". Did you for get to register the correct service?", uid));

        return service_inst;
    }

    public static <V extends AbstractService> V getService(String name, Class<V> service) {
        return getService(name);
    }

    public static <V extends AbstractService> V getService(long uid, Class<V> service) {
        return getService(uid);
    }

    //@CallerSensitive
    protected static <T extends AbstractService> void register(String name, long uid, Class<T> service) {
        ServiceManager inst = getInstance();

        if (inst.m_finalized)
            throw new IllegalServiceRequest("Illegal registration call after registry finalization. Did you forget to register the Service during program intialization?");
        else if (service.getDeclaredConstructors().length != 1)
            throw new InvalidServiceException("Illegal use of constructor in Service definition, use init() instead.");
        else if (service.getModifiers() != (Modifier.FINAL | Modifier.PUBLIC))
            throw new InvalidServiceException("Illegal modifiers in Service definition, must define service as 'public final' and CANNOT include 'abstract', 'static' or any other modifiers.");
        else if (service.getDeclaringClass() != null)
            throw new InvalidServiceException("Illegal Service defintion, must be a singleton top level class definition.");

        if (inst.m_registry_name.containsKey(name)) {
            AbstractService _inst = inst.m_registry_name.get(name);

            if (service.equals(_inst.getClass()))
                throw new InvalidServiceException("Illegal multiple registration calls from service definition.");
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
            inst.m_registry_uid.put(  uid, service_inst);
    }

    public void initialize() {
        if (!CallStack.checkFor(org.frc.team5409.churro.Main.class))
            throw new CallSecurityException("Illegal initialization of Service Manager outside of main().");
        else if (m_finalized)
            return;
        
        for (var entry : m_registry_name.entrySet()) {
            Exception e = callService(entry.getValue(), entry.getValue()::initialize);
            if (e != null)
                new ServiceExecutionException(String.format("Service \"%s\" encountered error during initialization.", entry.getKey()), e).printStackTrace();
        }
        m_finalized = true;
    }

    public void startServices() {
        if (m_running)
            return;

        for (var entry : m_registry_name.entrySet()) {
            Exception e = callService(entry.getValue(), entry.getValue()::start);
            if (e != null)
                new ServiceExecutionException(String.format("Service \"%s\" encountered error during start.", entry.getKey()), e).printStackTrace();
        }
        m_running = true;
    }

    public void stopServices() {
        if (!m_running)
            return;

        for (var entry : m_registry_name.entrySet()) {
            Exception e = callService(entry.getValue(), entry.getValue()::stop);
            if (e != null)
                new ServiceExecutionException(String.format("Service \"%s\" encountered error during stop.", entry.getKey()), e).printStackTrace();
            else
                entry.getValue().Service.alert(true);
        }
        m_running = false;
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