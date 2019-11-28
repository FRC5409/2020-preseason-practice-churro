package org.usfirst.frc5409.Testrobot.subsystems;

import org.usfirst.frc5409.Testrobot.navx.SPICom;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * NavX subsystem.
 * 
 * Facilitates the control and accessing
 * of Kaui Labs hardware through a 
 * software interface.
 */
public class NavX extends Subsystem {
    private SPICom m_spi_com;

    /**
     * Initialize NavX Subsystem
     */
    public NavX() {
        m_spi_com = new SPICom();
        
        //Establish communication with NavX
        if (m_spi_com.init()) {
            //success
        } else {
            //Failure
        }
    }


    @Override
    protected void initDefaultCommand() {

    }

}