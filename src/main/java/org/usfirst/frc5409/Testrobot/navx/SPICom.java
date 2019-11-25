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

    //Modifiable constants
    private static final int   com_clock_hz        = 500000; //[500,000 - 4,000,000]
    private static final int   com_lost_attempts   = 40; //Communication is considered lost after "x" successive errors

    //No-touchy constants
    private static final byte  com_req_len         = 3;
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

        return true;
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
        if (reg > com_address_space)
            return ComResult.NOADDRESS;
        
        ComResult res = ComResult.SUCCESS;
        byte data[] = new byte[com_req_len];
            data[0] = (byte) (reg & com_write_flag);
            data[1] = value;
            data[2] = getCRC(data, 2);
        
        synchronized(m_this_lock) {
            if (m_spi_com.write(data, com_req_len) != com_req_len) {
                res = ComResult.FAILED;
                m_successive_err_count++;
            } else
                m_successive_err_count = 0;
        }

        return res;
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
        if ((short)reg + (short)numReg > com_address_space_s) //Convert to short to prevent an overflow
            return ComResult.NOADDRESS;                       //Might remove since conv. is probably costly

        ComResult res = ComResult.SUCCESS;
        byte data[] = new byte[com_req_len];
            data[0] = reg;
            data[1] = numReg;
            data[2] = getCRC(data, 2);

            
        out = new byte[numReg+1];
        synchronized(m_this_lock) {
            comAttempt: {
                if (m_spi_com.write(out, com_req_len) != com_req_len) {
                    res = ComResult.FAILED;
                    m_successive_err_count++;
                    break comAttempt; //Communication failed
                }
                    
                Timer.delay(0.0002); //200 microsecond wait period

                if (m_spi_com.read(true, out, out.length) != out.length) {
                    res = ComResult.FAILED;
                    m_successive_err_count++;
                    break comAttempt; //Communication failed
                }

                if (getCRC(out, out.length) != 0) {
                    res = ComResult.BADCRC; //Communication succeded, but got bad data
                    m_successive_err_count++;
                } else
                    m_successive_err_count = 0; //Communication succeded
            }
        }

        return res;
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
        ComResult res = read(reg, (byte) 1, _out);
        out = _out[0];
        return res;
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