package org.frc.team5409.churro.uinput;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;

public final class IController {
    public enum Controller {
        kMainDriver,
        kSecondaryDriver
    }

    public enum Button {
        kA,
        kB,
        kX,
        kY
    }

    public enum Hand {
        kLeft,
        kRight
    }

    private XboxController m_controller;

    private IButton        m_btn_A, m_btn_B, m_btn_X, m_btn_Y; // A, B, X, Y buttons

    private IButton        m_bmp_lft, m_bmp_rht;               // Left and Right bumpers

    private ITrigger       m_trg_lft, m_trg_rht;               // Left and Right triggers

    private IJoystick      m_sck_lft, m_sck_rht;               // Left and Right joysticks
    
    private IButton        m_sbn_lft, m_sbn_rht;               // Left and Right joystick buttons

    private double         m_rumble_lft, m_rumble_rht;
    private Object         m_rumble_sync;

    private double         m_last_query;

    public IController(int port) {
        m_controller = new XboxController(port);

        m_btn_A = new IButton();
        m_btn_B = new IButton();
        m_btn_X = new IButton();
        m_btn_Y = new IButton();

        m_bmp_lft = new IButton();
        m_bmp_rht = new IButton();

        m_trg_lft = new ITrigger();
        m_trg_rht = new ITrigger();

        m_sck_lft = new IJoystick();
        m_sck_rht = new IJoystick();

        m_sbn_lft = new IButton();
        m_sbn_rht = new IButton();

        m_rumble_lft = 0;
        m_rumble_rht = 0;

        m_rumble_sync = new Object();

        m_last_query = Timer.getFPGATimestamp();
    }

    public IButton getButton(Button button) {
        switch(button) {
            case kA: return m_btn_A;
            case kB: return m_btn_B;
            case kX: return m_btn_X;
            case kY: return m_btn_Y;
        }
        return null;
    }

    public IButton getBumper(Hand hand) {
        switch(hand) {
            case kLeft:  return m_bmp_lft;
            case kRight: return m_bmp_rht;
        }
        return null;
    }

    public IButton getJoystickButton(Hand hand) {
        switch(hand) {
            case kLeft:  return m_sbn_lft;
            case kRight: return m_sbn_lft;
        }
        return null;
    }

    public IJoystick getJoystick(Hand hand) {
        switch(hand) {
            case kLeft:  return m_sck_lft;
            case kRight: return m_sck_rht;
        }
        return null;
    }

    public ITrigger getTrigger(Hand hand) {
        switch(hand) {
            case kLeft:  return m_trg_lft;
            case kRight: return m_trg_rht;
        }
        return null;
    }

    public void setRumble(double influence, double intensity) {
        influence = clamp(influence);
        intensity = clamp(intensity);

        synchronized(m_rumble_sync) {
            m_rumble_lft = intensity*influence + m_rumble_lft*(1-influence);
            m_rumble_rht = intensity*influence + m_rumble_rht*(1-influence);
        }
    }

    public void setRumble(double lft_influence, double lft_intensity, double rht_influence, double rht_intensity) {
        lft_influence = clamp(lft_influence);
        lft_intensity = clamp(lft_intensity);
        rht_influence = clamp(rht_influence);
        rht_intensity = clamp(rht_intensity);

        synchronized(m_rumble_sync) {
            m_rumble_lft = lft_intensity*lft_influence + m_rumble_lft*(1-lft_influence);
            m_rumble_rht = rht_intensity*rht_influence + m_rumble_rht*(1-rht_influence);
        }
    }
    
    public synchronized void query() {
        final double query_now_t = Timer.getFPGATimestamp();

        m_btn_A.query(m_controller.getRawButton(XboxController.Button.kA.value));
        m_btn_B.query(m_controller.getRawButton(XboxController.Button.kB.value));
        m_btn_X.query(m_controller.getRawButton(XboxController.Button.kX.value));
        m_btn_Y.query(m_controller.getRawButton(XboxController.Button.kY.value));

        m_bmp_lft.query(m_controller.getRawButton(XboxController.Button.kBumperLeft.value));
        m_bmp_rht.query(m_controller.getRawButton(XboxController.Button.kBumperRight.value));

        m_trg_lft.query(m_controller.getRawAxis(2));
        m_trg_rht.query(m_controller.getRawAxis(3));

        m_sck_lft.query(m_controller.getRawAxis(0),
                        m_controller.getRawAxis(1));

        m_sck_rht.query(m_controller.getRawAxis(4),
                        m_controller.getRawAxis(5));

        m_sbn_lft.query(m_controller.getRawButton(XboxController.Button.kStickLeft.value));
        m_sbn_rht.query(m_controller.getRawButton(XboxController.Button.kStickRight.value));

        double rumble_lft, rumble_rht;
        synchronized(m_rumble_sync) {
            rumble_lft = m_rumble_lft;
            rumble_rht = m_rumble_rht;

            m_rumble_lft = Math.max(0, m_rumble_lft - query_now_t + m_last_query); //Time drop-off (1rmbl / sec)
            m_rumble_rht = Math.max(0, m_rumble_rht - query_now_t + m_last_query) ;
        }

        HAL.setJoystickOutputs((byte) m_controller.getPort(), 0,  (short) (rumble_lft * 65535), (short) (rumble_rht * 65535));

        m_last_query = query_now_t;
    }

    private double clamp(double v) {
        if (v > 1)
            return 1;
        else if (v < 0)
            return 0;
        return v;
    }
}