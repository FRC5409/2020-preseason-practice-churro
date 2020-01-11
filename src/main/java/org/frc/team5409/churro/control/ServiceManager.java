package org.frc.team5409.churro.control;

import java.lang.reflect.Modifier;
import java.util.Hashtable;

import org.frc.team5409.churro.control.exception.*;

public final class ServiceManager {
    private static ServiceManager             m_instance;
    
    private        AbstractService[]          m_registry;

    private        Hashtable<String, Integer> m_name_hash;
    private        Hashtable<  Long, Integer> m_uid_hash;

    private        boolean                    m_finalized;
    private        boolean                    m_running;

    static {
        m_instance = new ServiceManager();
    }

    public static ServiceManager getInstance() {
        return m_instance;
    }

    private ServiceManager() {
        m_registry = new AbstractService[0];     
        
        m_name_hash = new Hashtable<>();
        m_uid_hash = new Hashtable<>();

        m_finalized = false;
        m_running = false;
    }
    
    public void initialize() {
        if (!CallStack.checkFor(org.frc.team5409.churro.Main.class))
            throw new CallSecurityException("Illegal initialization of Service Manager outside of main().");
        else if (m_finalized)
            return;
        
        for (var inst : m_registry) {
            //inst.Service.setInitFlag(true);
            inst.initialize();
        }
        m_finalized = true;
    }

    public void startServices() {
        if (m_running)
            return;

        for (var inst : m_registry) {
            inst.start();
        }
        m_running = true;
    }

    public void stopServices() {
        if (!m_running)
            return;
            
        for (var inst : m_registry) {
            inst.ServiceRunner.interrupt();
        }

        for (var inst : m_registry) {
            inst.ServiceRunner.join();
            inst.stop();
        }
        m_running = false;
    }


    @SuppressWarnings({ "unchecked" })
    public static <T extends AbstractService> T getService(String name) {
        Integer index = getInstance().m_name_hash.get(name);

        if (index == null)
            throw new IllegalServiceRequest(String.format("Attempted to get unknown service \"%s\". Did you for get to register \"%s\"?", name, name));

        return (T) getInstance().m_registry[index];
    }

    @SuppressWarnings({ "unchecked" })
    public static <T extends AbstractService> T getService(long uid) {
        Integer index = getInstance().m_uid_hash.get(uid);

        if (index == null)
            throw new IllegalServiceRequest(String.format("Attempted to get unknown service with uid \"%d\". Did you for get to register the correct service?", uid));

        return (T) getInstance().m_registry[index];
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

        if (inst.m_name_hash.containsKey(name)) {
            AbstractService _inst = inst.m_registry[inst.m_name_hash.get(name)];

            if (service.equals(_inst.getClass()))
                throw new InvalidServiceException("Illegal multiple registration calls from service definition.");
            else
                throw new InvalidServiceException(String.format("Service \"%s\" already exists with name %s.", _inst.getClass().getSimpleName(), name));
        } else if (inst.m_uid_hash.containsKey(uid)) {
            AbstractService _inst = inst.m_registry[inst.m_uid_hash.get(uid)];

            if (service.equals(_inst.getClass()))
                throw new InvalidServiceException("Illegal multiple registration calls from service definition.");
            else
                throw new InvalidServiceException(String.format("Service \"%s\" already exists with UID %d.", _inst.getClass().getSimpleName(), uid));
        }
        
        int index = inst.m_registry.length;

        inst.m_registry = java.util.Arrays.copyOf(inst.m_registry, index+1);
        inst.m_registry[index] = ServiceFactory.create(name, uid, service);
        inst.m_name_hash.put(name, index);
        inst.m_uid_hash.put(  uid, index);
    }
}