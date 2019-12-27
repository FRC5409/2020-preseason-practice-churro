package org.frc.team5409.churro.experimental.flow;

import java.lang.annotation.*;

@Inherited
@Documented

@Target({ElementType.TYPE, ElementType.TYPE_USE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FlowPolicy {
    public FPolicy value();
}