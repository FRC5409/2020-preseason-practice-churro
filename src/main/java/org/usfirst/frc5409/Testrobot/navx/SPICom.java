package org.usfirst.frc5409.Testrobot.navx;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.Timer;

/**
 * SPI Communication class for NavX.
 */
public class SPICom {
    static {
        com_crc7_table = buildCRCLookupTable();
    }

    private SPI                m_spi_com;
    private Object             m_this_lock;
    private int                m_successive_err_count;
    private boolean            m_is_initialized;

    //Modifiable constants
    private static final int   com_clock_hz        = 500000; //[500,000 - 4,000,000]
    private static final int   com_lost_attempts   = 40; //Communication is considered lost after "x" successive errors

    //No-touchy constants
    private static final byte  com_req_len         = 3;
    private static final byte  com_who_am_i        =  0x32; 
    private static final byte  com_crc7_poly       = -0x6F; //0x91 in signed form
    private static final byte  com_write_flag      = -0x80; //Could be incorrect
    private static final byte  com_address_space   =  0x6F;
    private static final short com_address_space_s =  0x6F; //Ensure Memoization
    private static final byte  com_crc7_table[];            //Memoization for crc calcs
    
    /**
     * Construct SPI Com Class.
     */
    public SPICom() {
        m_successive_err_count = 0;
        m_is_initialized = false;
        m_this_lock = new Object();
    }

    /**
     * Initialize Communication with NavX.
     */
    public boolean init() {
        m_spi_com = new SPI(Port.kMXP);
            m_spi_com.setMSBFirst();
            m_spi_com.setClockRate(com_clock_hz);
            m_spi_com.setClockActiveLow();
            m_spi_com.setChipSelectActiveLow();
            m_spi_com.setSampleDataOnTrailingEdge();      

        //NavX must have 12 secs of no motion at startup, TODO: integrate this into timing
        Timer.delay(0.0002); //200 microsecond wait period, Might not even need this

        checkDevice: {
            //Confirm that SPI device is in fact a NavX
            if (!confirmWhoIAm())
                break checkDevice;
            
            //Confirm that NavX went through initialization
            if (!confirmDeviceInit())
                break checkDevice; 
            
            //Confirm that NavX went through testing phase
            if (!confirmTestStatus())
                break checkDevice;

            //Confirm that NavX went through calibration phase
            if (!confirmCalStatus())//TODO: see which comes first, Calibration or testing
                break checkDevice;

            //Successfully established communication with NavX
            m_is_initialized = true;
            return true;
        }

        //Failed to establish communication with NavX. (Unidentified SPI device)
        forceClose();
        return false;
    }

    /**
     * Write data to NavX at specified register.
     * 
     * @param reg   Register location
     * @param value Write value
     * 
     * @return Communication Result.
     * 
     * @see ComResult
     */
    public ComResult write(byte reg, byte value) {
        if (!m_is_initialized)
            return ComResult.NOCONNECTION;

        if (reg > com_address_space)
            return ComResult.NOADDRESS;
        
        ComResult result = ComResult.SUCCESS;
        byte data[] = new byte[com_req_len];
            data[0] = (byte) (reg & com_write_flag);
            data[1] = value;
            data[2] = getCRC(data, 2);
        
        synchronized(m_this_lock) {
            if (m_spi_com.write(data, com_req_len) != com_req_len) {
                result = ComResult.FAILED;
                m_successive_err_count++;
            } else
                m_successive_err_count = 0;
        }

        return result;
    }

    /**
     * Read data from NavX from specified registers.
     * 
     * @param reg    Register location
     * @param numReg Subsequent registers to read
     * @param out    Data out
     * 
     * @return Communication Result.
     * 
     * @see ComResult
     */
    public ComResult read(byte reg, byte numReg, byte out[]) {
        if (!m_is_initialized)
            return ComResult.NOCONNECTION;

        if ((short)reg + (short)numReg > com_address_space_s) //Convert to short to prevent an overflow
            return ComResult.NOADDRESS;                       //Might remove since conv. is probably costly

        ComResult result = ComResult.SUCCESS;
        byte data[] = new byte[com_req_len];
            data[0] = reg;
            data[1] = numReg;
            data[2] = getCRC(data, 2);

            
        out = new byte[numReg+1];
        synchronized(m_this_lock) {
            comAttempt: {
                if (m_spi_com.write(out, com_req_len) != com_req_len) {
                    result = ComResult.FAILED;
                    m_successive_err_count++;
                    break comAttempt; //Communication failed
                }
                    
                Timer.delay(0.0002); //200 microsecond wait period

                if (m_spi_com.read(true, out, out.length) != out.length) {
                    result = ComResult.FAILED;
                    m_successive_err_count++;
                    break comAttempt; //Communication failed
                }

                if (getCRC(out, out.length) != 0) {
                    result = ComResult.BADCRC; //Communication succeded, but got bad data
                    m_successive_err_count++;
                } else
                    m_successive_err_count = 0; //Communication succeded
            }
        }

        return result;
    }

    /**
     * Read byte of data from NavX at specified register.
     * 
     * @param reg Register location
     * @param out Byte out
     * 
     * @return Communication Result.
     * 
     * @see ComResult
     */
    public ComResult read(byte reg, byte out) {
        byte _out[] = new byte[1];
        ComResult result = read(reg, (byte) 1, _out);
        out = _out[0];
        return result;
    }

    /**
     * Read data from NavX at specified registers.
     * 
     * @param regs Registers
     * @param out  Data out
     * 
     * @return Communication Result.
     * 
     * @see ComResult
     */
    public ComResult read(Regs regs, byte out[]) {
        return read(regs.rx, regs.nx, out);
    }

    /**
     * Forces communication with spi device to close;
     */
    private void forceClose() {
        m_spi_com.close();
        m_is_initialized = false;
    }

    /**
     * Confirm WhoAmI of SPI Device.
     * Checks to see whether the device is actually the NavX.
     * 
     * @return Result of test
     */
    private boolean confirmWhoIAm() {
        int err_count = 0;

        short who_am_i = -1;
        byte data = -1;
        while (err_count < com_lost_attempts) {
            ComResult res = read(RegisterMap.REG_WHOAMI, data);
            if (res.success) {
                who_am_i = Conv.decodeUnsignedByte(data);
                return (who_am_i == com_who_am_i);
            } else
                err_count++;
        }

        return false;
    }

    /**
     * Confirm that NavX went through initialization.
     * 
     * @return Result of test
     */
    private boolean confirmDeviceInit() {
        int err_count = 0;

        byte op_status = -1;
        while (err_count < com_lost_attempts) {
            ComResult res = read(RegisterMap.REG_OP_STATUS, op_status);
            if (res.success) {
                switch(op_status) {
                    case BoardStatus.OP.INITIALIZING:            continue;
                    case BoardStatus.OP.SELFTEST_IN_PROGRESS:    continue;
                    case BoardStatus.OP.ERROR:                   return false;
                    case BoardStatus.OP.IMU_AUTOCAL_IN_PROGRESS: continue;
                    case BoardStatus.OP.NORMAL:                  return true;
                }
            } else
                err_count++;
        }

        return false;
    }

    /**
     * Confirm that NavX went through it's testing phase.
     * 
     * @return Result of test
     */
    private boolean confirmTestStatus() {
        int err_count = 0;

        byte test_status = -1;
        while (err_count < com_lost_attempts) {
            ComResult res = read(RegisterMap.REG_SELFTEST_STATUS, test_status);
            if (res.success) {
                if (test_status == BoardStatus.SELFTEST.COMPLETE)
                    return true;
            } else
                err_count++;
        }
        return false;
    }

    /**
     * Confirm that NavX went through it's calibration phase.
     * 
     * @return Result of test
     */
    private boolean confirmCalStatus() {
        int err_count = 0;

        byte cal_status = -1;
        while (err_count < com_lost_attempts) {
            ComResult res = read(RegisterMap.REG_CAL_STATUS, cal_status);
            if (res.success) {
                if (cal_status == BoardStatus.CAL.COMPLETE);
                    return true;
            } else
                err_count++;
        }
        return false;
    }

    /**
     * Generate CRC from data. (Cyclic Redundancy Check)
     * Credit to Kaui labs for CRC implementation
     * 
     * @param data Byte Data 
     * @param len Length of data
     * 
     * @return CRC-7 byte
     */
    private static byte getCRC(byte data[], int len) {
        byte crc = 0;
        
        for (int i = 0; i < len; i++) {
            crc ^= data[i];
            crc = com_crc7_table[crc];
        }
        return crc;
    }

    /**
     * Generate CRC lookup tables for faster computation. (Cyclic Redundancy Check)
     * Credit to Kaui labs for CRC implementation
     * 
     * @return CRC-7 memoization table
     */
    private static byte[] buildCRCLookupTable() {
        byte table[] = new byte[256];
        byte crc;

        for (int i = 0; i < 256; i++ ) {
            crc = (byte) i;
            for (int j = 0; j < 8; j++) {
                if ((crc & 1) == 1)
                    crc ^= com_crc7_poly;
                crc >>= 1;
            }
            table[i] = crc;
        }
        return table;
    }
}