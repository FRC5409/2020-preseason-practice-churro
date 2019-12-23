package org.frc.team5409.churro.flow;

import java.util.Hashtable;

import org.frc.team5409.churro.flow.exception.*;

public final class Flow {
    private static Hashtable<Integer, FlowPort> m_ports;

    private static long                         m_latest_id;
    private static boolean                      m_initialized = false;

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
            throw new InvalidPortException(String.format("Invalid port at #%d.", port));
        else if (fpoint.isConnected())
            throw new OccupiedFlowException("Flow point is occupied with another connection");

        FlowPort fport = m_ports.get(port);
        if (fport == null)
            throw new InvalidPortException(String.format("Invalid port #%d, port does not exist.", port));
        else if (fport.isFlowing())
            throw new OccupiedFlowException(String.format("Port #%d is occupied.", port));
        else if (!fport.identity.check(fpoint.getIdentity()))
            throw new FlowIdentityException(String.format("Identity at port #%d does not match flow point identity.", port));
        
        fport.lock();

        if (fport.fpoint0 != null) {
            if (!FPolicy.isCompatible(fport.fpoint0.getPolicy(), fpoint.getPolicy()))
                throw new FlowPolicyException(String.format("Flow policy \"%s\" of flow point #0 on port #%d is incompatible with policy \"%s\" on attempted connection.", fport.fpoint0.getPolicy().toString(), port, fpoint.getPolicy().toString()));

            fport.fpoint1 = fpoint;
            fport.hasFlow = true;

            fpoint.setPort(fport, fport.fpoint0);
            fport.fpoint0.setPort(fport, fport.fpoint1);
        } else if (fport.fpoint1 != null) {
            if (!FPolicy.isCompatible(fport.fpoint0.getPolicy(), fpoint.getPolicy()))
                throw new FlowPolicyException(String.format("Flow policy \"%s\" of flow point #1 on port #%d is incompatible with policy \"%s\" on attempted connection.", fport.fpoint1.getPolicy().toString(), port, fpoint.getPolicy().toString()));

            fport.fpoint0 = fpoint;
            fport.hasFlow = true;

            fpoint.setPort(fport, fport.fpoint1);
            fport.fpoint1.setPort(fport, fport.fpoint0);
        } else {
            fport.fpoint0 = fpoint;

            fpoint.setPort(fport, null);
        }

        fport.unlock();
    }

    public static synchronized void disconnect(FlowPoint fpoint) {
        if (!fpoint.isConnected())
            return;

        FlowPort fport = fpoint.getPort();
        
        fport.lock();

        if (fport.hasFlow) {
            if (fpoint.getId() == fport.fpoint0.getId()) {
                fport.fpoint0 = null;

                fpoint.setPort(null, null);
                fport.fpoint0.setPort(fport, null);
            } else {
                fport.fpoint1 = null;

                fpoint.setPort(null, null);
                fport.fpoint1.setPort(fport, null);
            }
            fport.hasFlow = false;
        } else {
            fport.fpoint0 = null;
            fport.fpoint1 = null;

            fpoint.setPort(null, null);
        }

        fport.unlock();
    }

    protected static FlowIdentifier newIdentity(FTypePolicy policy, Class<?>... ftypes) {
        return new FlowIdentifier(policy, ftypes);
    }
     
    protected static synchronized long obtainId() {
        return m_latest_id++;
    }
}