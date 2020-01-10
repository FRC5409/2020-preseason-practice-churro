package org.frc.team5409.churro.control;

final class ServiceFactory {
    private ServiceFactory() {
    }

    protected static <T extends AbstractService> T create(String name, long uid, Class<T> service) {
        try {
            T inst = service.getConstructor().newInstance();
                inst.Service = new ServiceBase(name, uid);
                inst.ServiceLogger = new ServiceLogger(name, uid);
                inst.ServiceRunner = new ServiceRunner(uid);
                inst.Service.setInitFlag(false);
            return inst;
        } catch (Exception e) {
            throw new RuntimeException("Undefined behaviour during Service creation.", e);
        }
    }
}