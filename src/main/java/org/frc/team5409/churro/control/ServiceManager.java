package org.frc.team5409.churro.control;

import java.lang.reflect.Modifier;
import java.util.Hashtable;

import org.frc.team5409.churro.control.exception.*;

@SuppressWarnings({ "unchecked" })
public final class ServiceManager {
    private static Hashtable<Long, AbstractService> m_registry;
    private static boolean                          m_finalized;

    static {
        m_finalized = false;
        m_registry = new Hashtable<>();
    }

    private ServiceManager() { // TODO: maybe remove?
        throw new CallSecurityException("Illegal initialization of Service Manager.");
    }
    
    public static final void init() {
        if (!CallStack.checkFor(org.frc.team5409.churro.Main.class))
            throw new CallSecurityException("Illegal initialization of Service Manager outside of main().");
        else if (m_finalized)
            return;
        
        m_finalized = true;
        m_registry.entrySet().forEach( inst -> inst.getValue().init() ); // TODO: Should it be multithreaded?
    }

    //@CallerSensitive
    public static final <T extends AbstractService> T get(Class<T> service) {
        if (!CallStack.checkFor(AbstractService.class) && !CallStack.checkFor(ServiceRegistor.class))
            throw new CallSecurityException("Illegal Service request. Services may not request other services");

        T inst;
        try {
            inst = (T) m_registry.get(service.getField("serviceUID").getLong(null));
        } catch (Exception e) {
            throw new RuntimeException("Undefined behaviour during Service request.");
        }              

        if (inst == null)
            throw new IllegalServiceRequest(String.format("Attempted to get unregistered Service \"%s\". Did you for get to register \"%s\"?", service.getSimpleName(), service.getSimpleName()));
        
        return inst;
    }

    //@CallerSensitive
    protected static final <T extends AbstractService> void register(String name, Class<T> service) {
        if (m_finalized)                   // This exception might never throw \/ \/ \/
            throw new IllegalServiceRequest("Illegal registration call after registry finalization. Did you forget to register the Service?");
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

        if (m_registry.get(uid) != null) {
            AbstractService _inst = m_registry.get(uid);

            if (service.equals(_inst.getClass()))
                throw new InvalidServiceException("Illegal multiple registration calls from service definition.");
            else
                throw new InvalidServiceException(String.format("Service \"%s\" already exists with UID %d.", _inst.getName(), uid));
        }

        m_registry.put(uid, ServiceFactory.create(name, uid, service));
    }
}