package org.frc.team5409.churro.control;

import org.frc.team5409.churro.control.exception.CallSecurityException;

@SuppressWarnings("unchecked")
public final class ServiceRegistrant {
    protected ServiceRegistrant() {
    }

    public void register(String name) {
        ServiceManager.register(name, getCaller());
    }

    public void register() {
        var service = getCaller();
        ServiceManager.register(service.getSimpleName(), service);
    }

    private Class<? extends AbstractService> getCaller() {
        Class<?> caller = CallStack.searchFor(AbstractService.class);
    
        if (caller == null)
            throw new CallSecurityException("Illegal Service call.");
        else
            return (Class<? extends AbstractService>) caller;
    }
}