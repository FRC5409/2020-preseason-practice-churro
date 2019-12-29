package org.frc.team5409.churro.control;

import org.frc.team5409.churro.control.exception.CallSecurityException;

@SuppressWarnings("unchecked")
abstract class ServiceUtility {
    public static void register(String name) {
        ServiceManager.register(name, getCaller());
    }

    public static void register() {
        var service = getCaller();
        ServiceManager.register(service.getSimpleName(), service);
    }

    public static Thread useThread(Runnable target) {
        ServiceBase service = ServiceManager.get(getCaller());

        if (service.m_thread == null)
            service.m_thread = new Thread(target, String.format("Service Thread [%d]", service.getUID()));
        
        return service.m_thread;
    }

    private static Class<? extends AbstractService> getCaller() {
        Class<?> caller = CallStack.searchFor(ServiceBase.class);
    
        if (caller == null)
            throw new CallSecurityException("Illegal Service Utility call.");
        else
            return (Class<? extends AbstractService>) caller;
    }
}