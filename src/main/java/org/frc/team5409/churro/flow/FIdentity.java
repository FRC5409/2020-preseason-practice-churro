package org.frc.team5409.churro.flow;

public final class FIdentity {
    private final FTypePolicy m_policy;
    private final Class<?>    m_ftypes[];

    @FunctionalInterface
    private interface ClassComparison {
        public boolean compare(Class<?> A,  Class<?> B);
    }

    protected FIdentity(FTypePolicy policy, Class<?>... ftypes) {
        m_policy = policy;
        m_ftypes = ftypes;
    }

    protected <T> boolean check(FTypePolicy policy, T... fobjects) {
        if (m_ftypes.length != fobjects.length)
            return false;

        ClassComparison comparef = (policy == FTypePolicy.LOOSE) ? FIdentity::looseCheck : 
                                                                   FIdentity::strictCheck;
        for (int i=0; i<m_ftypes.length; i++) {
            if (fobjects[i] != null && !compare(m_ftypes[i], fobjects[i].getClass(), comparef))
                return false;
        }
        
        return true;
    }

    protected boolean check(FTypePolicy policy, Class<?>... ftypes) {
        if (m_ftypes.length != ftypes.length)
        return false;

        ClassComparison comparef = (policy == FTypePolicy.LOOSE) ? FIdentity::looseCheck : 
                                                                   FIdentity::strictCheck;
        for (int i=0; i<m_ftypes.length; i++) {
            if (ftypes[i] != null && !compare(m_ftypes[i], ftypes[i], comparef))
                return false;
        }

        return true;
    }

    protected boolean check(FTypePolicy policy, FIdentity iother) {
        return check(policy, iother.m_ftypes);
    }
    
    protected <T> boolean check(T... fobjects) {
        return check(m_policy, fobjects);
    }

    protected boolean check(Class<?>... ftypes) {
        return check(m_policy, ftypes);
    }

    protected boolean check(FIdentity iother) {
        return check(m_policy, iother);
    }

    private static boolean compare(Class<?> A, Class<?> B, ClassComparison comparef) {
        // Extract primitive type from java lang equivalent if primitive exists.
        try { A = (Class<?>) A.getField("TYPE").get(null); } 
        catch(Exception e) { }

        // i.e. Class<Integer>  ->  Class<int>
        try { B = (Class<?>) B.getField("TYPE").get(null);  }
        catch(Exception e) { } // Exception indicates that no primitive type was found

        return comparef.compare(A, B);
    }
    
    private static boolean looseCheck(Class<?> A, Class<?> B) {
        return A.isAssignableFrom(B);
    }

    private static boolean strictCheck(Class<?> A, Class<?> B) {
        return A.equals(B);
    }
}