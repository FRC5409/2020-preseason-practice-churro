package org.frc.team5409.churro.flow;

import java.lang.annotation.*;

@Inherited
@Documented

@Target({ElementType.TYPE, ElementType.TYPE_USE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FlowIdentity {
    public FTypePolicy policy() default FTypePolicy.LOOSE; 
    public Class<?>[] identity() default {};
    public Class<?>[] value() default {};
}