package org.frc.team5409.churro.control;

/**
 * Service information utility.
 * 
 * <p> Specifies basic information of a service
 * such as it's name, uid, etc. </p>
 * 
 * @see AbstractService
 */
public final class ServiceBase {
	private final String  m_name;
    private final long    m_uid;
    private       boolean m_did_run;
    
    protected ServiceBase(String name, long uid) {
        m_name = name;
        m_uid = uid;
        m_did_run = false;
    }

    public String getName() {
        return new String(m_name);
    }

    public long getUID() {
        return Long.valueOf(m_uid);
    }

    public boolean isFirstRun() {
        return !m_did_run;
    }

    protected void alert(boolean did_run) {
        m_did_run = did_run;
    }
}
    
   