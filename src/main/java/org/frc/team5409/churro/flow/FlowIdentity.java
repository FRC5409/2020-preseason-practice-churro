package org.frc.team5409.churro.flow;

import java.lang.annotation.*;

@Inherited
@Documented

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FlowIdentity {
    public FTypePolicy policy(); 
    public Class<?>[] identity(); 
}