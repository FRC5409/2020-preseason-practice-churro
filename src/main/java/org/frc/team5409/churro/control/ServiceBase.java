package org.frc.team5409.churro.control;

import org.frc.team5409.churro.control.exception.CallSecurityException;

class ServiceBase {
	private   String m_name;
    private   long   m_uid;
    
    protected Thread m_thread;

    protected ServiceBase() {
        if (!CallStack.checkFor(ServiceFactory.class))
            if (!CallStack.checkFor(ServiceBase.class)) // TODO: Figure out why constructor fires twice
                throw new CallSecurityException("Illegal construction of Service.");
        
        m_thread = null;
    }

    public final String getName() {
        return new String(m_name);
    }

    public final long getUID() {
        return Long.valueOf(m_uid);
    }
}
    
   