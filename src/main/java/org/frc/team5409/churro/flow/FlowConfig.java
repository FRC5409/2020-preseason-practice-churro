package org.frc.team5409.churro.flow;

import org.frc.team5409.churro.flow.exception.FlowConfigException;

public final class FlowConfig {
    protected FlowPort ports[] = null;
    protected int      nports = 0;

	public FlowConfig allocate(int nports) {
        if (ports != null)
            throw new FlowConfigException("Attempted to reallocate port information during flow configuration.");
        else if (nports <= 0)
            throw new FlowConfigException(String.format("Attempted to allocate \'%d\' ports during flow configuration. Must be number greater than one.", nports));

        ports = new FlowPort[nports];

        return this;
	}

    
    public FlowConfig assign(String name, int port, FIdentity identity) {
        if (port < 0)
            throw new FlowConfigException(String.format("Attempted to assign at invalid port #\'%d\' du-ring flow configuration.", port));
        else if (nports == ports.length)
            throw new FlowConfigException("Not enough ports allocated during flow configuration.");
        else if (!isUnique(port))
            throw new FlowConfigException(String.format("Port #%d is already in use.", port));

        ports[nports] = new FlowPort(name, port, identity);
        nports++;

        return this;
    }

    public FlowConfig assign(String name, int port, FTypePolicy policy, Class<?>... ftypes) {
        return assign(name, port, new FIdentity(policy, ftypes));
    }

    public FlowConfig assign(String name, int port, Class<?>... ftypes) {
        return assign(name, port, new FIdentity(FTypePolicy.LOOSE, ftypes));
    }

    private boolean isUnique(int port) {
        for (int i=0; i<nports; i++) {
            if (port == ports[i].port)
                return false;
        }

        return true;
    }
}