package org.frc.team5409.churro.control;

import org.frc.team5409.churro.control.exception.CallSecurityException;

class ServiceBase {
	private final String m_name;
	private final long   m_uid;

    protected ServiceBase() {
        throw new CallSecurityException("Illegal construction of Service.");
    }

    protected ServiceBase(String name, long uid) {
        if (!CallSecurity.checkFor(Services.class))
            throw new CallSecurityException("Illegal construction of Service.");

        m_name = name;
        m_uid = uid;
    }

    public final String getName() {
        return m_name;
    }

    public final long getUID() {
        return m_uid;
    }
}
    
   