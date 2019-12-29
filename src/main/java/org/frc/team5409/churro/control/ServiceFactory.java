package org.frc.team5409.churro.control;

import java.lang.reflect.Field;

final class ServiceFactory {
    private Field m_ref_name;
    private Field m_ref_uid;

    protected ServiceFactory() {
        try {
            m_ref_name = ServiceBase.class.getDeclaredField("m_name");
                m_ref_name.setAccessible(true);

            m_ref_uid = ServiceBase.class.getDeclaredField("m_uid");
                m_ref_uid.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException("Undefined behaviour during Service reflection.", e);
        }
    }

    protected <T extends AbstractService> T create(String name, long uid, Class<T> service) {
        T inst;
        try {
            inst = service.getConstructor().newInstance();
                m_ref_name.set(inst, name);
                m_ref_uid.set(inst, uid);
        } catch (Exception e) {
            throw new RuntimeException("Undefined behaviour during Service creation.", e);
        }

        return inst;
    }

    protected void finish() {
        m_ref_name.setAccessible(false);
        m_ref_uid.setAccessible(false);
    }
}