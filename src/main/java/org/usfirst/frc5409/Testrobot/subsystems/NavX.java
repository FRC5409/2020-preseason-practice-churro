package org.usfirst.frc5409.Testrobot.subsystems;

import org.usfirst.frc5409.Testrobot.navx.SPICom;

import edu.wpi.first.wpilibj.command.Subsystem;

public class NavX extends Subsystem {
    private SPICom m_spi_com;


    /**
     * Initializes NavX Subsystem
     */
    public NavX() {
        m_spi_com = new SPICom();

        //Initialize communication with NavX
        boolean res = m_spi_com.init();

    }


    @Override
    protected void initDefaultCommand() {

    }

}