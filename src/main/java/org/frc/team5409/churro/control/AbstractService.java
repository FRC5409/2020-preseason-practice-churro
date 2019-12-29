package org.frc.team5409.churro.control;


public abstract class AbstractService extends ServiceBase {
    protected abstract void init();

    protected abstract void start();
    
    protected abstract void stop();

    protected void run() {
        // Override me !!!
        System.out.println(getName());
    };

    protected final static class Service extends ServiceUtility { // Proxy
        private Service() {
        }
    }
}