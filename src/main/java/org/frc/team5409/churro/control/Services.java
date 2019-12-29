package org.frc.team5409.churro.control;

import java.lang.StackWalker.Option;
import java.util.stream.Collectors;
import java.lang.reflect.Field;
import java.util.Hashtable;

import org.frc.team5409.churro.control.exception.*;
import org.frc.team5409.churro.control.AbstractService;


@SuppressWarnings({"unused", "unchecked"})
public final class Services {
    private static Hashtable<Long, AbstractService> m_registry;
    private static boolean                  m_finalized;

    static {
        m_registry = new Hashtable<>();
        m_finalized = false;
    }

    private Services() {
    }
    
    public static final void start() {
        m_finalized = true;
        
        m_registry.entrySet().forEach( //TODO: Should it be multithreaded?
            inst -> inst.getValue().init()
        );
    }

    //@CallerSensitive
    public static final <T extends AbstractService> T get(Class<T> service) {
        if (!CallSecurity.checkFor(ServiceBase.class))
            throw new IllegalServiceRequest("Illegal Service request. Services may not utilize other services");
        
        T inst;
        try {
            inst = (T) m_registry.get(service.getField("serviceUID").getLong(null));
        } catch (Exception e) { // Should never throw
            throw new IllegalServiceRequest("Undefined behaviour during Service request.");
        }              

        if (inst == null)
            throw new IllegalServiceRequest(String.format("Attempted to get unregistered Service \"%s\".", service.getSimpleName()));
        
        return inst;
    }

    //@CallerSensitive
    protected static final <T extends AbstractService> void register(String name, Class<?> service) {
        if (m_finalized)                   // This exception may never throw \/ \/ \/
            throw new IllegalServiceRequest("Illegal registration call after registry finalization. Did you forget to register the Service?");
        else if (!ServiceBase.class.isAssignableFrom(service))
            throw new InvalidServiceException("Illegal registration call from non-Service.");
        else if (service.getDeclaredConstructors().length != 1)
            throw new InvalidServiceException("Illegal use of constructor in Service definition, use init() instead.");

        long uid;
        try {
            uid = service.getField("serviceUID").getLong(null);
        } catch (Exception e) {
            throw new InvalidServiceException("Attempted to register Service with no UID, did you define long serviceUID?");
        }

        if (m_registry.get(uid) != null) {
            ServiceBase _inst = m_registry.get(uid);

            if (_inst.getClass().equals(service))
                throw new InvalidServiceException("Illegal multiple registration calls from service definition.");
            else
                throw new InvalidServiceException(String.format("Service \"%s\" already exists with UID %d.", _inst.getName(), uid));
        }

        m_registry.put(uid, (T) new ServiceBase(name, uid)); //TODO: Check these casts
    }
}