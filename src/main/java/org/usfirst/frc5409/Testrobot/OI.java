package org.usfirst.frc5409.Testrobot;

import org.usfirst.frc5409.Testrobot.util.JoystickType;
import edu.wpi.first.wpilibj.XboxController;

/**
 * Operator Interface
 */
public class OI {
    /**
     * Initialize operator interface
     */
    public OI() {
        main_joystick = new XboxController(0);
        secondary_joystick = new XboxController(0);
    }

    /**
     * Get specified joystick
     * 
     * @param joystick_type Joystick type
     * 
     * @return Joystick
     */
    public XboxController getJoystick(JoystickType joystick_type) {
        switch(joystick_type) {
            case MAIN:      return main_joystick;
            case SECONDARY: return secondary_joystick;
            
            default:        return main_joystick;
        }
    }

    /**
     * Main Joystick
     */
    private final XboxController main_joystick;

    /**
     * Secondary Joystick
     */
    private final XboxController secondary_joystick;
}

