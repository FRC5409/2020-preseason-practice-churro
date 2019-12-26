package org.frc.team5409.churro.flow;

public enum FPolicy {
    PROVIDER         (  1),
    RECEIVER         (  2),
    COMMUNICATOR     (-10),
    PASSIVE_PROVIDER (  4),
    PASSIVE_RECEIVER (  5);

    private FPolicy(int compat_id) {
        m_compat_id = compat_id;
    }

    protected static boolean isCompatible(FPolicy A, FPolicy B) {
        switch(A.m_compat_id + B.m_compat_id) {
            case  2: return false; // P and P
            case  3: return true;  // P and R
            case  4: return false; // R and R
            case  5: return false; //PP and P
            case  6: return true;  //PR and P or PP and R
            case  7: return false; //PR and R
            case  8: return false; //PP and PP
            case  9: return false; //PR and PP
            case 10: return false; //PR and PR
            default: return true;  //Communicator
        }
    }

    private final int m_compat_id;
}