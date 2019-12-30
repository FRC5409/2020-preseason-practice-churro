package org.frc.team5409.churro.control;

public final class ServiceBase {
	private final String m_name;
    private final long   m_uid;
    
    protected ServiceBase(String name, long uid) {
        m_name = name;
        m_uid = uid;
    }

    public final String getName() {
        return new String(m_name);
    }

    public final long getUID() {
        return Long.valueOf(m_uid);
    }
}
    
   