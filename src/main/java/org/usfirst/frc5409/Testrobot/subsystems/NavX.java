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
            System.out.println("NavX Connected");//success
        } else {
            //Failure
        }
    }

    //TODO: UN-JANK THESE FUNCTIONS, THEY HAVE NO SAFETY AND WILL PROBABLY CATASTROPHICALLY FAIL SOME DAY
    public void zeroYaw() {
        m_navx_com.write(RegisterMap.REG_INTG_CTRL, IntgCtrl.RESET_YAW);
    }

    public YPRHData getYPRH() {
        byte data[] = new byte[YPRHData.packet_length];

        m_navx_com.read(RegisterMap.REGS_YPRH_DATA, data);
        return YPRHData.fromRaw(data);
    }

    public GXYZData getGXYZ() {
        byte data[] = new byte[GXYZData.packet_length];

        m_navx_com.read(RegisterMap.REGS_GXYZ_DATA, data);
        return GXYZData.fromRaw(data);
    }

    public QXYZWData getQXYZW() {
        byte data[] = new byte[QXYZWData.packet_length];

        m_navx_com.read(RegisterMap.REGS_QWXYZ_DATA, data);
        return QXYZWData.fromRaw(data);
    }

    public IVXYZData getIVXYZ() {
        byte data[] = new byte[IVXYZData.packet_length];

        m_navx_com.read(RegisterMap.REGS_IVXYZ, data);
        return IVXYZData.fromRaw(data);
    }

    public IDXYZData getIDXYZ() {
        byte data[] = new byte[IDXYZData.packet_length];

        m_navx_com.read(RegisterMap.REGS_IDXYZ, data);
        return IDXYZData.fromRaw(data);
    }

    @Override
    protected void initDefaultCommand() {
    }

    @Override
    public void periodic() {
        
    }
}