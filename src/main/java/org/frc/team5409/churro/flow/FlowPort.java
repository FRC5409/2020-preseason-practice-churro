package org.frc.team5409.churro.flow;

final class FlowPort {
    public FlowPort(String name, int port, int index, Class<?>... fparams) {
        this.name = name;
        this.port = port;
        this.index = index;
        this.fparams = fparams;
    }

    public final String    name;
    public final int       port;
    public final int       index;
    public final Class<?>  fparams[];

    public FlowPoint       fpoint0 = null;
    public FlowPoint       fpoint1 = null;
    
}