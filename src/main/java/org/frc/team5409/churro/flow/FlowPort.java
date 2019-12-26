package org.frc.team5409.churro.flow;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.*;

final class FlowPort {
    protected final String         name;
    protected final int            port;
    protected final FIdentity      identity;

    protected volatile FlowPoint   fpoint0;
    protected volatile FlowPoint   fpoint1;

    private         AtomicBoolean  m_has_flow;
    private         ReadWriteLock  m_port_lock;
    private         Lock           m_port_sync;

    protected FlowPort(String name, int port, FIdentity identity) {
        this.name = name;
        this.port = port;
        this.identity = identity;

        this.fpoint0 = null;
        this.fpoint1 = null;

        this.m_has_flow = new AtomicBoolean(false);
        this.m_port_sync = new ReentrantLock(true);
        this.m_port_lock = new ReentrantReadWriteLock(true);
    }

    @SuppressWarnings("incomplete-switch")
    protected void lock(FLockType lock) {
        switch (lock) {
            case MODIFY_PORT: m_port_lock.writeLock().lock(); break;
            case USE_PORT:    m_port_lock.readLock().lock(); break;
            case PORT_SYNC:   m_port_sync.lock();           break;
        }
    }

    @SuppressWarnings("incomplete-switch")
    protected void unlock(FLockType lock) {
        switch (lock) {
            case MODIFY_PORT: m_port_lock.writeLock().unlock(); break;
            case USE_PORT:    m_port_lock.readLock().unlock(); break;
            case PORT_SYNC:   m_port_sync.unlock();           break;
        }
    }

    protected void setFlow(boolean flow) {
        m_has_flow.set(flow);
    }

    protected boolean isFlowing() {
        return m_has_flow.get();
    }

    protected boolean isNull() {
        return (port == NULLPORT);
    }

    protected static final int       NULLPORT = -1;
    protected static final FlowPort  NullPort = new FlowPort(null, NULLPORT, null);
}