package org.usfirst.frc5409.Testrobot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc5409.Testrobot.navx.RegisterMap;
import org.usfirst.frc5409.Testrobot.navx.NavXCom;

/**
 * NavX subsystem.
 * 
 * Facilitates the control and accessing
 * of Kaui Labs hardware through a 
 * software interface.
 */
public class NavX extends Subsystem {
    private NavXCom m_navx_com;

    /**
     * Initialize NavX Subsystem
     */
    public NavX() {
        m_navx_com = new NavXCom();
        
        //Establish communication with NavX
        if (m_navx_com.init()) {
            //success
        } else {
            //Failure
        }
    }

    @Override
    protected void initDefaultCommand() {
    }

}