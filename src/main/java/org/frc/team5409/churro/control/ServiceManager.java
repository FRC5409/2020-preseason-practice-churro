package org.frc.team5409.churro.control;

import java.lang.reflect.Modifier;
import java.util.Hashtable;

import org.frc.team5409.churro.control.exception.*;

public final class ServiceManager {
    private static Hashtable<String, Integer> m_name_hash;
    private static Hashtable<  Long, Integer> m_uid_hash;
    private static AbstractService[]          m_registry;
    private static boolean                    m_finalized;

    static {
        m_finalized = false;
        m_name_hash = new Hashtable<>();
        m_uid_hash = new Hashtable<>();
        m_registry = new AbstractService[0];
    }

    private ServiceManager() { // TODO: maybe remove?
        throw new CallSecurityException("Illegal initialization of Service Manager.");
    }
    
    public static final void init() {
        if (!CallStack.checkFor(org.frc.team5409.churro.Main.class))
            throw new CallSecurityException("Illegal initialization of Service Manager outside of main().");
        else if (m_finalized)
            return;
        
       for (var inst : m_registry) {
            inst.Service.setInitFlag(true);
            inst.init();
        }
        m_finalized = true;
    }

    
    @SuppressWarnings({ "unchecked" })
    public static final <T extends AbstractService> T get(String name, Class<T> service) {
        Integer index = m_name_hash.get(name);

        if (index == null)
            throw new IllegalServiceRequest(String.format("Attempted to get unregistered Service \"%s\". Did you for get to register \"%s\"?", name, name));

        return (T) m_registry[index];
    }

    @SuppressWarnings({ "unchecked" })
    public static final <T extends AbstractService> T get(String name) {
        Integer index = m_name_hash.get(name);

        if (index == null)
            throw new IllegalServiceRequest(String.format("Attempted to get unregistered Service \"%s\". Did you for get to register \"%s\"?", name, name));

        return (T) m_registry[index];
    }


    //@CallerSensitive
    protected static final <T extends AbstractService> void register(String name, Class<T> service) {
        if (m_finalized)
            throw new IllegalServiceRequest("Illegal registration call after registry finalization. Did you forget to register the Service during program intialization?");
        else if (service.getDeclaredConstructors().length != 1)
            throw new InvalidServiceException("Illegal use of constructor in Service definition, use init() instead.");
        else if (service.getModifiers() != (Modifier.FINAL | Modifier.PUBLIC))
            throw new InvalidServiceException("Illegal modifiers in Service definition, must define service as 'public final' and CANNOT include 'abstract', 'static' or any other modifiers.");
        else if (service.getDeclaringClass() != null)
            throw new InvalidServiceException("Illegal Service defintion, must be a singleton top level class definition.");

        long uid;
        try {
            uid = service.getField("serviceUID").getLong(null);
        } catch (Exception e) {
            throw new InvalidServiceException("Attempted to register Service with no UID, did you define long serviceUID?");
        }

        if (m_uid_hash.get(uid) != null) {
            AbstractService _inst = m_registry[m_uid_hash.get(uid)];

            if (service.equals(_inst.getClass()))
                throw new InvalidServiceException("Illegal multiple registration calls from service definition.");
            else
                throw new InvalidServiceException(String.format("Service \"%s\" already exists with UID %d.", _inst.getName(), uid));
        } else if (m_name_hash.get(name) != null) {
            AbstractService _inst = m_registry[m_name_hash.get(name)];

            if (service.equals(_inst.getClass()))
                throw new InvalidServiceException("Illegal multiple registration calls from service definition.");
            else
                throw new InvalidServiceException(String.format("Service \"%s\" already exists with name %s.", _inst.getName(), name));
        }
        
        m_registry = java.util.Arrays.copyOf(m_registry, m_registry.length+1);
        int index = m_registry.length-1;

        m_registry[index] = ServiceFactory.create(name, uid, service);
        m_name_hash.put(name, index);
        m_uid_hash.put(uid, index);
    }
}