package org.frc.team5409.churro.experimental.flow;

import java.util.concurrent.locks.*;
import java.lang.reflect.*;

import org.frc.team5409.churro.experimental.flow.exception.*;

@SuppressWarnings("unused")
public abstract class FlowPoint {
    private final FIdentity      m_identity;
    private final FPolicy        m_policy;
    private final long           m_id;

    private       FlowPort       m_fport = null;
    private       FlowPoint      m_fsibling = null;

    private       Method         m_onReceive = null;
    private       Method         m_onProvide = null; //Might delete

    private       Object         m_fbuffer[] = null;
    private       Thread         m_fthread;
    private       Lock           m_sync;
    public FlowPoint() {
        Class<?> _class = getClass();

        // Identify flow object policy
        FlowPolicy policy;
        getPolicy: {
            boolean is_defined = false;
            
            // Try for class annotation (class definition)
            policy = _class.getAnnotation(FlowPolicy.class);
            if (policy != null)
                is_defined = true;

            // Try for type_use annotation (full inline definition)
            FlowPolicy _policy = _class.getAnnotatedSuperclass().getAnnotation(FlowPolicy.class);
            if (_policy != null) {
                if (is_defined) // Cannot annotate policies in multiple areas.
                    throw new FlowInitException("Attempted to override class defined flow policies in constructor, check code."); 
                else {
                    is_defined = true;
                    policy = _policy;
                }
            }
            
            //Unable to identify flow policy annotation
            if (!is_defined)
                throw new FlowInitException("Flow definition has no policy. Did you forget to define the flow slot?");
        }
        m_policy = policy.value();


        // Identify flow object identity
        FlowIdentity identity;
        getIdentity: {
            boolean is_defined = false;

            // Try for class annotation (class definition)
            identity = _class.getAnnotation(FlowIdentity.class);
            if (identity != null)
                is_defined = true;

            // Try for type_use annotation (full inline definition)
            FlowIdentity _identity = _class.getAnnotatedSuperclass().getAnnotation(FlowIdentity.class);
            if (_identity != null) {
                if (is_defined) // Cannot annotate identities in multiple areas.
                    throw new FlowInitException("Attempted to override class defined flow identities in constructor, check code."); 
                else {
                    is_defined = true;
                    identity = _identity;
                }
            }

            //Unable to identify flow identity annotation
            if (!is_defined)
                throw new FlowInitException("Flow definition has no identity, check code.");
        }
        m_identity = new FIdentity(identity.policy(), 
                            (identity.identity().length==0) ? identity.value() : identity.identity());

        // Identify flow objects methods
        for (Method method : _class.getMethods()) {
            FlowMethod method_type = method.getDeclaredAnnotation(FlowMethod.class);

            if (method_type == null)
                continue;

            method.setAccessible(true);
            switch (method_type.value()) {
                case PROVIDE: {
                    if (m_onProvide != null)
                        throw new FlowInitException("Declared multiple provide methods, check code.");
                    else if (!m_identity.check(FTypePolicy.STRICT, method.getParameterTypes()))
                        throw new FlowInitException("Provide function parameters do not match that of the implementing object's identity, check code.");
                    m_onProvide = method;
                    continue;
                }
                case RECEIVE: {
                    if (m_onReceive != null)
                        throw new FlowInitException("Declared multiple receive methods, check code.");
                    else if (!m_identity.check(FTypePolicy.STRICT, method.getParameterTypes()))
                        throw new FlowInitException("Receive function parameters do not match that of the implementing object's identity, check code.");
                    m_onReceive = method;
                    continue;
                }
            }
        }

        m_fthread = new Thread();
        m_sync = new ReentrantLock(false);
        m_id = Flow.obtainId();
    }

    protected final <T> void send(T... args) {
        assertT(args);

        if (m_fport == null) // TODO: Should it throw here?
            return; 
        else if (!m_fport.isFlowing())
            return;

        m_fport.lock(FLockType.USE_PORT);
            attemptSend: {
                if (!m_fport.isFlowing()) // Check again because disconnection may have occured during
                    break attemptSend;    // the time this thread was waiting in queue for the lock. 
                
                m_fport.lock(FLockType.PORT_SYNC);
                    m_fsibling.m_sync.lock();
                        m_fsibling.m_fbuffer = args; // Copy data over to sibling buffer
                    m_fsibling.m_sync.unlock();
                m_fport.unlock(FLockType.PORT_SYNC);
            }
        m_fport.unlock(FLockType.USE_PORT);
    }

    protected final synchronized void setPort(FlowPort fport) {
        if (fport == null) // Defense against rogue function calls
            return;

        if (m_fthread.isAlive()) { // Wait for flow thread to finish execution
            Flow.safe( () -> m_fthread.join() );
            m_fbuffer = null; // Flush buffer to prevent flow-rush at next flow thread execution
        }

        if (fport.isNull()) {
            m_fport = null;
            m_fsibling = null;
        } else {
            m_fport = fport;
            m_fsibling = (this.equals(fport.fpoint0)) ? fport.fpoint1 :
                                                        fport.fpoint0;
            if (m_fsibling != null) // Start flow thread if connection is established
                (m_fthread = new Thread(this::flow)).start();
        }
    }

    protected final synchronized int getPort() { // Only returns int for safety reasons
        return m_fport.port;
    }

    protected final FPolicy getPolicy() {
        return m_policy;
    }

    protected final FIdentity getIdentity() {
        return m_identity;
    }

    protected final long getId() {
        return m_id;
    }

    protected final synchronized boolean isConnected() {
        return (m_fport != null);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        else
            return ((FlowPoint) obj).m_id == this.m_id;
    }

    private final <T> void receive(T... args) {
        assertT(args);
        
        if (m_onReceive != null)
            invoke(m_onReceive, args);
    }

    private <T> void invoke(Method method, T... args) {
        try {
            m_onReceive.invoke(this, args);
        } catch (Exception e) {
            throw new FlowException("Flow function encountered and exception during execution.", e);
        }
    }

    private <T> void assertT(T... args) {
        if (!m_identity.check(args))
            throw new FlowIdentityException("Arguments do not match flow objects identity.");
    }

    private void flow() {
        while (m_fport.isFlowing()) {
            m_sync.lock();
            if (m_fbuffer != null) {
                receive(m_fbuffer);

                m_fbuffer = null; // Flush buffer after execution

                m_sync.unlock();
            } else {
                m_sync.unlock();

                Thread.yield();
            }
        }
    }
}