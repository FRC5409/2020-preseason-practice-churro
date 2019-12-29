package org.frc.team5409.churro.control;

import java.lang.StackWalker.Option;

public abstract class AbstractService extends ServiceBase {
    protected abstract void init();

    protected abstract void start();
    
    protected abstract void stop();

    protected final static class Service {
        private Service() {
        }

        @Deprecated
        public static void register(String name, Class<? extends AbstractService> service) {
            Services.register(service.getSimpleName(), service);
        }

        @Deprecated
        public static void register(Class<? extends AbstractService> service) {
            Services.register(service.getSimpleName(), service);
        }

        public static void register(String name) {
            Class<?> service = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE).getCallerClass();
            Services.register(name, service);
        }

        public static void register() {
            Class<?> service = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE).getCallerClass();
            Services.register(service.getSimpleName(), service);
        }
    }
}