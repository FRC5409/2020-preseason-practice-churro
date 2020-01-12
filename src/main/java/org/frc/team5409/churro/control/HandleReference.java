package org.frc.team5409.churro.control;

public final class HandleReference extends EventHandle {
    private RunnableHandle m_handles[];

    public HandleReference(RunnableHandle... handles) {
        m_handles = handles;
    }
    
    @Override
    protected void bind(EventEmitter emitter) {
        // Shouldn't ever be called
    }

    @Override
    public void unbind() {
        for (var handle : m_handles) {
            if (handle.getEmitter() != null)
                handle.unbind();
        }
    }

    protected RunnableHandle getHandle(int index) {
        return m_handles[index];
    }
}