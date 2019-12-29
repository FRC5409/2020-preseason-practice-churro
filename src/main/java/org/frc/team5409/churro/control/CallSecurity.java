package org.frc.team5409.churro.control;

import java.lang.StackWalker.Option;
import java.util.stream.Collectors;

final class CallSecurity {
    private CallSecurity() {
    }

    public static final boolean checkFor(Class<?> caller) {
        return !StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE)
                           .walk( s -> s.filter( f -> f.getDeclaringClass().isAssignableFrom(caller) )
                                        .collect( Collectors.toList())
                                ).isEmpty();
    }
}