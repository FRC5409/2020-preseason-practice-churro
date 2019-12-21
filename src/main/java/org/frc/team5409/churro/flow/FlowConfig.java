package org.frc.team5409.churro.flow;

import org.frc.team5409.churro.flow.exception.FlowConfigException;

public final class FlowConfig {
    protected FlowPort m_ports[] = null;
    protected int      m_nports = 0;

	public FlowConfig allocate(int nports) {
        if (m_ports != null)
            throw new FlowConfigException("Attempted to reallocate port information during flow configuration.");
        else if (nports <= 0)
            throw new FlowConfigException(String.format("Attempted to allocate \'%d\' ports during flow configuration.", nports));

        m_ports = new FlowPort[nports];

        return this;
	}

    public FlowConfig assign(String name, int port, Class<?>... fparams) {
        if (port < 0)
            throw new FlowConfigException(String.format("Attempted to assign at invalid port #\'%d\' du-ring flow configuration.", port));
        else if (m_nports == m_ports.length)
            throw new FlowConfigException("Not enough ports allocated during flow configuration.");
        else if (!isUnique(port))
            throw new FlowConfigException(String.format("Port #%d is already in use.", port));

        m_ports[m_nports] = new FlowPort(name, port, m_nports, fparams);
        m_nports++;

        return this;
    }

    private boolean isUnique(int port) {
        for (FlowPort fport : m_ports) {
            if (port == fport.port)
                return false;
        }

        return true;
    }
}