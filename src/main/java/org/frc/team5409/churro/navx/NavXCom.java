package org.frc.team5409.churro.navx;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SPI.Port;


/**
 * SPI Communication class for NavX.
 */
public class NavXCom {
    private SPI                m_spi_com;
    private Object             m_this_lock;
    private int                m_err_count;
    private boolean            m_is_initialized;

    //Modifiable constants
    private static final int   com_clock_hz        = 500000; //[500,000 - 4,000,000]
    private static final int   com_board_hz        = 200;    //[4 - 200]
    private static final int   com_lost_attempts   = 40;     //Communication lost after "x" successive errors

    //No-touchy constants
    private static final byte  com_req_len         =  0x03;
    private static final byte  com_who_am_i        =  0x32; 
    private static final byte  com_write_flag      = -0x80; //Could be incorrect
    private static final int   com_address_space   =  0x6F;
    
    /**
     * Construct NavX Com Class.
     */
    public NavXCom() {
        m_err_count = 0;
        m_is_initialized = false;
        m_this_lock = new Object();
    }

    /**
     * Initialize Communication with NavX.
     */
    public boolean init() {
        if (m_is_initialized)
            return true;

        m_spi_com = new SPI(Port.kMXP);
            m_spi_com.setMSBFirst();
            m_spi_com.setClockRate(com_clock_hz);
            m_spi_com.setClockActiveLow();
            m_spi_com.setChipSelectActiveLow();
            m_spi_com.setSampleDataOnTrailingEdge();      

        //NavX must have 12 secs of no motion at startup, TODO: integrate this into timing
        Timer.delay(0.0002); //200 microsecond wait period, Might not even need this

        checkDevice: {
            //Temporarily Fake initialization to fool read/write blocks
            m_is_initialized = true;

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

            //Set NavX update rate
            write(RegMap.REG_UPDATE_RATE, (byte)com_board_hz);

            //Successfully established communication with NavX
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
            data[0] = (byte) (reg | com_write_flag);
            data[1] = value;
            data[2] = getCRC(data, 2);
        
        synchronized(m_this_lock) {
            if (m_spi_com.write(data, com_req_len) != com_req_len) {
                result = ComResult.FAILED;
                m_err_count++;
            } else
                m_err_count = 0;
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

        if ((int)(reg + numReg) > com_address_space) //Convert to short to prevent an overflow
            return ComResult.NOADDRESS;                       //Might remove since conv. is probably costly

        ComResult result = ComResult.SUCCESS;
        byte data_out[] = new byte[com_req_len];
            data_out[0] = reg;
            data_out[1] = numReg;
            data_out[2] = getCRC(data_out, com_req_len-1);

            
        byte data_in[] = new byte[numReg+1];
        synchronized(m_this_lock) {
            comAttempt: {
                int bytes_out = m_spi_com.write(data_out, com_req_len);
                if (bytes_out != com_req_len) {
                    result = ComResult.FAILED;
                    m_err_count++;
                    break comAttempt; //Communication failed
                }
                    
                Timer.delay(0.0002); //200 microsecond wait period

                int bytes_in = m_spi_com.read(true, data_in, data_in.length);
                if (bytes_in != data_in.length) {
                    result = ComResult.FAILED;
                    m_err_count++;
                    break comAttempt; //Communication failed
                }

                if (getCRC(data_in, numReg) != data_in[numReg]) {
                    result = ComResult.BADCRC; //Communication succeded, but got bad data
                    m_err_count++;
                } else
                    m_err_count = 0; //Communication succeded
            }
        }

        System.arraycopy(data_in, 0, out, 0, data_in.length-1);
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
    public ComResult read(byte reg, byte out[]) {
        ComResult result = read(reg, (byte) 1, out);
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
    public ComResult read(NavXRegs regs, byte out[]) {
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

        byte who_am_i[] = new byte[1];
        while (err_count < com_lost_attempts) {
            ComResult res = read(RegMap.REG_WHOAMI, who_am_i);
            if (res.success) 
                return (who_am_i[0] == com_who_am_i);
            else
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

        byte op_status[] = new byte[1];
        while (err_count < com_lost_attempts) {
            ComResult res = read(RegMap.REG_OP_STATUS, op_status);
            if (res.success) {
                switch(op_status[0]) {
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

        byte test_status[] = new byte[1];
        while (err_count < com_lost_attempts) {
            ComResult res = read(RegMap.REG_SELFTEST_STATUS, test_status);
            if (res.success) {
                if ((test_status[0] & BoardStatus.SELFTEST.COMPLETE) != 0)
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

        byte cal_status[] = new byte[1];
        while (err_count < com_lost_attempts) {
            ComResult res = read(RegMap.REG_CAL_STATUS, cal_status);
            if (res.success) {
                if ((cal_status[0] & BoardStatus.CAL.COMPLETE) != 0);
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
        int crc = 0;

        for (int i = 0; i < len; i++) {
            crc ^= (int)(0x00FF & data[i]);
            for (int j = 0; j < 8; j++) {
                if ((crc & 0x0001) != 0)
                    crc ^= 0x0091;
                crc >>= 1;
            }
        }

        return (byte) crc;
    }
}