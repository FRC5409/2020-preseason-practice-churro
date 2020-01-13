package org.frc.team5409.churro.system;

import java.util.Hashtable;

import org.frc.team5409.churro.system.exception.BadNameException;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * <h2> SystemContainer </h2>
 * 
 * Manages and contains subsystems and commands
 * for use in the robot program.
 */
public abstract class SystemContainer {
    private Hashtable<String, Subsystem> m_subsystems;
    private Hashtable<String, Command>   m_commands;

    /**
     * Constructs System Container.
     */
    public SystemContainer() {
        m_subsystems = new Hashtable<>();
        m_commands = new Hashtable<>();
    }

    /**
     * Initializes System Container.
     * 
     * <p> This method defines all subsystems
     * and commands stored in this container </p>
     * 
     * @see #addSubsystem(String, Subsystem)
     * @see #addCommand(String, Command)
     */
    protected abstract void initialize();

    /**
     * Gets subsystem with {@code name} from
     * this container.
     * 
     * @param <T>  The subsystem type
     * 
     * @param name The name of the subsystem
     * 
     * @return Subsystem with {@code name}.
     * 
     * @throws BadNameException Thrown when subsystem with {@code name} does not exist.
     */
    @SuppressWarnings("unchecked")
    public final <T extends Subsystem> T getSubsystem(String name) {
        T system = (T) m_subsystems.get(name);
        if (system == null)
            throw new BadNameException("Attempted to get unknown subsystem with name \""+name+"\".");

        return system;
    }

    /**
     * Gets command with {@code name} from
     * this container.
     * 
     * @param <T>  The command type
     * 
     * @param name The name of the command
     * 
     * @return Command with {@code name}.
     * 
     * @throws BadNameException Thrown when command with {@code name} does not exist.
     */
    @SuppressWarnings("unchecked")
    public final <T extends Command> T getCommand(String name) {
        T command = (T) m_commands.get(name);
        if (command == null)
            throw new BadNameException("Attempted to get unknown command with name \""+name+"\".");

        return command;
    }

    /**
     * Adds subsystem to container.
     * 
     * <pre> {@code
     *
     *...
     *    addSubsystem("ExampleSubsystem", new ExampleSubsystem());
     *... 
     * 
     *}</pre>
     * 
     * @param name   The subsystem name
     * @param system The subsystem
     */
    protected final Subsystem addSubsystem(String name, Subsystem system) {
        if (m_subsystems.containsKey(name))
            throw new BadNameException("Subsystem already exists with name \""+name+"\"."); //TODO: make exception
        else if (m_subsystems.containsValue(system))
            throw new BadNameException("Subsystem of same class registered multiple times.");

        m_subsystems.put(name, system);
        return system;
    }

    /**
     * Adds command to container.
     * 
     * <pre> {@code
     *
     *...
     *    addCommand("ExampleCommand", new ExampleCommand());
     *... 
     * 
     *}</pre>
     * 
     * @param name   The subsystem name
     * @param command The subsystem
     */
    protected final Command addCommand(String name, Command command) {
        if (m_commands.containsKey(name))
            throw new BadNameException("Command already exists with name \""+name+"\".");
        else if (m_commands.containsValue(command))
            throw new BadNameException("Command of same class registered multiple times.");
            
        m_commands.put(name, command);
        return command;
    }
}