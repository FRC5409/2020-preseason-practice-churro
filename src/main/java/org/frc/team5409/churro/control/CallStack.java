package org.frc.team5409.churro.control;

import java.lang.StackWalker.Option;
import java.util.stream.Collectors;

final class CallStack {
    private CallStack() {
    }

    public static final Class<?> getCaller() {
        return StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE).getCallerClass();
    }

    public static final Class<?> searchFor(Class<?> caller) {
        var stack = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE)
                        .walk( s -> s.filter( f -> caller.isAssignableFrom(f.getDeclaringClass()) )
                        .collect( Collectors.toList()));
                        
        if (stack.isEmpty())
            return null;
        else
            return stack.get(0).getDeclaringClass();
    }

    public static final boolean checkFor(Class<?> caller) {
        return !StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE)
                    .walk( s -> s.filter( f -> caller.isAssignableFrom(f.getDeclaringClass()) )
                    .collect( Collectors.toList()))
                .isEmpty();
    }
}