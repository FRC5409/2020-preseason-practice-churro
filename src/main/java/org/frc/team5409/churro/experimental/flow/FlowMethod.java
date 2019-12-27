package org.frc.team5409.churro.experimental.flow;

import java.lang.annotation.*;

@Inherited
@Documented

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FlowMethod {
    public FMethodType value();
}