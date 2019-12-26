package org.frc.team5409.churro.flow;

import java.util.Hashtable;

import org.frc.team5409.churro.flow.exception.*;

public final class Flow {
    private static Hashtable<Integer, FlowPort> m_ports;

    private static long                         m_latest_id;
    private static boolean                      m_initialized = false;

    @FunctionalInterface
    protected interface SafeCall {
        public void call() throws Throwable;
    }

    private Flow() {
    }

    public static void init(FlowConfig config) {
        if (m_initialized)
            return;

        m_ports = new Hashtable<>(config.nports, 1.0f);

        for (FlowPort fport : config.ports) {
            m_ports.put(fport.port, fport);
        }

        m_latest_id = Long.MIN_VALUE;
        m_initialized = true;
    }

    public static synchronized void connect(FlowPoint fpoint, int port) {
        if (port < 0)
            throw new InvalidPortException(String.format("Invalid Port at #%d.", port));
        else if (fpoint.isConnected())
            throw new OccupiedFlowException("Flow Point is occupied with another connection.");

        FlowPort fport = m_ports.get(port);
        if (fport == null)
            throw new InvalidPortException(String.format("Invalid Port #%d, does not exist.", port));
        else if (fport.isFlowing())
            throw new OccupiedFlowException(String.format("Port #%d is occupied.", port));
        else if (!fport.identity.check(fpoint.getIdentity()))
            throw new FlowIdentityException(String.format("Identity at port #%d does not match Flow Point Identity.", port));
        
        fport.lock(FLockType.MODIFY_PORT);
            if (fport.fpoint0 != null) {
                if (!FPolicy.isCompatible(fport.fpoint0.getPolicy(), fpoint.getPolicy()))
                    throw new FlowPolicyException(String.format("Flow policy \"%s\" of point 0 on port #%d is incompatible with \"%s\" policy on attempted connection.", fport.fpoint0.getPolicy().toString(), port, fpoint.getPolicy().toString()));

                fport.fpoint1 = fpoint;
                fport.setFlow(true);

                fport.fpoint0.setPort(fport);
                fport.fpoint1.setPort(fport);
            } else if (fport.fpoint1 != null) {
                if (!FPolicy.isCompatible(fport.fpoint1.getPolicy(), fpoint.getPolicy()))
                    throw new FlowPolicyException(String.format("Flow policy \"%s\" of point 0 on port #%d is incompatible with \"%s\" policy on attempted connection.", fport.fpoint1.getPolicy().toString(), port, fpoint.getPolicy().toString()));

                fport.fpoint0 = fpoint;
                fport.setFlow(true);

                fport.fpoint1.setPort(fport);
                fport.fpoint0.setPort(fport);
            } else {
                fport.fpoint0 = fpoint;

                fpoint.setPort(fport);
            }
        fport.unlock(FLockType.MODIFY_PORT);
    }

    public static synchronized void disconnect(FlowPoint fpoint) {
        if (!fpoint.isConnected())
            return;

        FlowPort fport = m_ports.get(fpoint.getPort()); //TODO: Maybe should account for "possible" null case
        
        fport.lock(FLockType.MODIFY_PORT);
            if (fport.isFlowing()) {
                fport.setFlow(false);
                
                if (fpoint.equals(fport.fpoint0)) {
                    fport.fpoint0 = null;
                    fport.fpoint1.setPort(fport);
                } else {
                    fport.fpoint1 = null;
                    fport.fpoint0.setPort(fport);
                }
            } else {
                fport.fpoint0 = null;
                fport.fpoint1 = null;
            }

            fpoint.setPort(FlowPort.NullPort);
        fport.unlock(FLockType.MODIFY_PORT);
    }

    protected static FIdentity newIdentity(FTypePolicy policy, Class<?>... ftypes) {
        return new FIdentity(policy, ftypes);
    }
     
    protected static synchronized long obtainId() {
        return m_latest_id++;
    }

    protected static Throwable safe(SafeCall wrapper) {
        try {
            wrapper.call();
            return null;
        } catch (Throwable e) {
            return e;
        }
    }
}