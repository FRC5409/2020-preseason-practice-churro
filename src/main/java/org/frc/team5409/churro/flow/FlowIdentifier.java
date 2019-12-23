package org.frc.team5409.churro.flow;

public final class FlowIdentifier {
    private final FTypePolicy m_policy;
    private final Class<?>    m_ftypes[];

    protected FlowIdentifier(FTypePolicy policy, Class<?>... ftypes) {
        m_policy = policy;
        m_ftypes = ftypes;
    }

    protected boolean check(Class<?> ftypes) {
        if (m_policy == FTypePolicy.LOOSE)
            return looseCheck(ftypes);
        else 
            return strictCheck(ftypes);
    }

    protected boolean check(FlowIdentifier other) {
        if (m_policy == FTypePolicy.LOOSE)
            return looseCheck(other.m_ftypes);
        else 
            return strictCheck(other.m_ftypes);
    }

    protected boolean looseCheck(Class<?>... o_ftypes) {
        if (m_ftypes.length != o_ftypes.length)
            return false;
        
        for (int i=0; i<m_ftypes.length; i++) {
            if (!m_ftypes[i].isAssignableFrom(o_ftypes[i]))
                return false;
        }

        return false;
    }
        
    protected boolean looseCheck(FlowIdentifier other) {
        return looseCheck(other.m_ftypes);
    }

    protected boolean strictCheck(Class<?>... o_ftypes) {
        if (m_ftypes.length != o_ftypes.length)
            return false;
        
        for (var i=0; i<m_ftypes.length; i++) {
            if (!m_ftypes[i].equals(o_ftypes[i]))
                return false;
        }

        return false;
    }

    protected boolean strictCheck(FlowIdentifier other) {
        return strictCheck(other.m_ftypes);
    }
}