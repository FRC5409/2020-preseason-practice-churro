package org.frc.team5409.churro.control;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceUID {
    public long value();
}