package org.frc.team5409.churro.flow;

import java.util.concurrent.locks.ReentrantLock;

final class FlowPort {
    public final String         name;
    public final int            port;
    public final int            index;
    public final FlowIdentifier identity;

    public       FlowPoint      fpoint0 = null;
    public       FlowPoint      fpoint1 = null;
    public       boolean        hasFlow = false;

    private       ReentrantLock  flow_lock = new ReentrantLock();

    public FlowPort(String name, int port, int index, FlowIdentifier identity) {
        this.name = name;
        this.port = port;
        this.index = index;
        this.identity = identity;
    }

    public void lock() {
        flow_lock.lock();
    }

    public void unlock() {
        flow_lock.unlock();
    }

    public synchronized boolean isFlowing() {
        return hasFlow;
    } 
}