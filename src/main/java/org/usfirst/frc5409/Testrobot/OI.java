package org.usfirst.frc5409.Testrobot;

import org.usfirst.frc5409.Testrobot.util.JoystickType;
import edu.wpi.first.wpilibj.XboxController;


public class OI {
    private final XboxController main_joystick;
    private final XboxController secondary_joystick;

    public OI() {
        main_joystick = new XboxController(0);
        secondary_joystick = new XboxController(0);
    }

    public XboxController getJoystick(JoystickType joystick_type) {
        switch(joystick_type) {
            case MAIN:      return main_joystick;
            case SECONDARY: return secondary_joystick;
            
            default:        return main_joystick;
        }
    }


}

