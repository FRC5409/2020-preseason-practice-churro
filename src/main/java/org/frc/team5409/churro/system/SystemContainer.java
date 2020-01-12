package org.frc.team5409.churro.system;

import java.util.Hashtable;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

public abstract class SystemContainer {
    private Hashtable<String, Subsystem> m_subsystems;
    private Hashtable<String, Command>   m_commands;

    public SystemContainer() {
        m_subsystems = new Hashtable<>();
        m_commands = new Hashtable<>();
    }

    protected abstract void initialize();

    @SuppressWarnings("unchecked")
    public final <T extends Subsystem> T getSubsystem(String name) {
        T system = (T) m_subsystems.get(name);
        if (system == null)
            throw new RuntimeException("Attempted to get unknown subsystem with name \""+name+"\".");

        return system;
    }

    @SuppressWarnings("unchecked")
    public final <T extends Command> T getCommand(String name) {
        T command = (T) m_commands.get(name);
        if (command == null)
            throw new RuntimeException("Attempted to get unknown subsystem with name \""+name+"\".");

        return command;
    }

    protected final void addSubsystem(String name, Subsystem system) {
        if (m_subsystems.containsKey(name))
            throw new RuntimeException("Subsystem already exists with name \""+name+"\"."); //TODO: make exception
        else if (m_subsystems.containsValue(system))
            throw new RuntimeException("Subsystem of same class registered multiple times.");

        m_subsystems.put(name, system);
    }

    protected final void addCommand(String name, Command command) {
        if (m_subsystems.containsKey(name))
            throw new RuntimeException("Command already exists with name \""+name+"\".");
        else if (m_commands.containsValue(command))
            throw new RuntimeException("Command of same class registered multiple times.");
            
        m_commands.put(name, command);
    }
}