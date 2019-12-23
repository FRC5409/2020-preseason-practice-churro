package org.frc.team5409.churro.flow;

import java.lang.reflect.Method;

import org.frc.team5409.churro.flow.exception.*;

@SuppressWarnings("unused")
public abstract class FlowPoint {
    private final FlowIdentifier m_identity;
    private final FPolicy        m_policy;
    private final long           m_id;

    private       FlowPort       m_connection = null;
    private       FlowPoint      m_sibling = null;

    private       Method         m_onReceive = null;
    private       Method         m_onProvide = null;
    private       Method         m_onRequest = null;

    public FlowPoint() {
        m_id = Flow.obtainId();

        Class<?> _class = this.getClass();

        FlowPolicy policy = _class.getAnnotation(FlowPolicy.class);
        if (policy == null)
            throw new FlowInitException("Flow definition has no policy, check code.");
        else m_policy = policy.key();
            
        FlowIdentity identity = _class.getAnnotation(FlowIdentity.class);
        if (identity == null)
            throw new FlowInitException("Flow definition has no identity, check code.");
        else m_identity = new FlowIdentifier(identity.policy(), identity.identity());

        for (Method method : _class.getMethods()) {
            FlowMethod method_type = method.getAnnotation(FlowMethod.class);

            if (method_type == null)
                continue;

            switch (method_type.key()) {
                case PROVIDE: {
                    if (m_onProvide != null)
                        throw new FlowInitException("Declared multiple provide methods, check code.");
                    if (!m_identity.strictCheck(method.getParameterTypes()))
                        throw new FlowInitException("Provide function parameters do not match the implementing object identity, check code.");
                    m_onProvide = method;
                    continue;
                }
                case RECEIVE: {
                    if (m_onReceive != null)
                        throw new FlowInitException("Declared multiple receive methods, check code.");
                    if (!m_identity.strictCheck(method.getParameterTypes()))
                        throw new FlowInitException("Receive function parameters do not match the implementing object identity, check code.");
                    m_onReceive = method;
                    continue;
                }
                case REQUEST: {
                    if (m_onRequest != null)
                        throw new FlowInitException("Declared multiple request methods, check code.");
                    if (!m_identity.strictCheck(method.getParameterTypes()))
                        throw new FlowInitException("Request function parameters do not match the implementing object identity, check code.");
                    m_onRequest = method;
                    continue;
                }
            }
        }
    }

    protected void request() {
        //Override me!
    }

    protected final void send() {

    }

    protected final synchronized void setPort(FlowPort fport, FlowPoint fsibling) {
        m_connection = fport;
        m_sibling = fsibling;
    }

    protected final synchronized FlowPort getPort() {
        return m_connection;
    }

    protected final FPolicy getPolicy() {
        return m_policy;
    }

    protected final FlowIdentifier getIdentity() {
        return m_identity;
    }

    protected final long getId() {
        return m_id;
    }

    protected final synchronized boolean isConnected() {
        return (m_connection != null);
    }
}