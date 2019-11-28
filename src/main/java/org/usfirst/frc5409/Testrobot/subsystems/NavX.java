package org.usfirst.frc5409.Testrobot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc5409.Testrobot.navx.RegisterMap;
import org.usfirst.frc5409.Testrobot.navx.IntgCtrl;
import org.usfirst.frc5409.Testrobot.navx.NavXCom;
import org.usfirst.frc5409.Testrobot.navx.data.*;

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

    public void zeroYaw() {
        m_navx_com.write(RegisterMap.REG_INTG_CTRL, IntgCtrl.RESET_YAW);
    }

    public YPRHData getYPRH() {//TODO: UN-JANK THESE FUNCTIONS
        return YPRHData.fromRaw();
    }

    @Override
    protected void initDefaultCommand() {
    }

}