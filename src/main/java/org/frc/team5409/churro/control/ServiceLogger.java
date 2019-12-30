package org.frc.team5409.churro.control;

import java.io.PrintStream;

public final class ServiceLogger {
    private final String       m_prefix_info;
    private final String       m_prefix_warn;
    private final String       m_prefix_error;

    private       PrintStream  m_stream;

    protected ServiceLogger(String name, long uid) {
        m_prefix_info = name+"["+uid+"] {INFO} - ";
        m_prefix_warn = name+"["+uid+"] {WARN} - ";
        m_prefix_error = name+"["+uid+"] {ERROR} - ";

        m_stream = new PrintStream(System.out); //TODO: Should it log to file?
    }

    public void log(String str) {
        m_stream.println(m_prefix_info+str);
    }

    public void logf(String format, Object... args) {
        m_stream.printf(m_prefix_info+format+"\n", args);
    }

    public void warn(String str) {
        m_stream.println(m_prefix_warn+str);
    }

    public void warnf(String format, Object... args) {
        m_stream.printf(m_prefix_warn+format+"\n", args);
    }

    public void error(String str) {
        m_stream.println(m_prefix_error+str);
    }

    public void errorf(String format, Object... args) {
        m_stream.printf(m_prefix_error+format+"\n", args);
    }
}