/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
//import edu.wpi.first.wpilibj.GenericHID;
//import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ControlType;
//import com.revrobotics.ColorSensorV3.RawColor;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
//import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import edu.wpi.first.wpilibj.DriverStation;


public class ControlPanel extends SubsystemBase {
  /**
   * Creates a new ControlPanal.
   */
  public I2C.Port i2cPort = I2C.Port.kOnboard;

  public ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

  public ColorMatch m_colorMatcher = new ColorMatch();

  //public Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);//blue
  //public Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  //public Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  //public Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  public Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);//blue
  public Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);//green
  public Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);//red
  public Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);//yellow

  private static char colorString;
  private static char FMScolor;
  private static char colorTesting;

  public static CANSparkMax NEO550;
  public static CANEncoder m_encoder;
  public static CANPIDController m_pidcontroller;

  public ControlPanel() {

    setColorSensor();
    setMotor();
  }

  /**
   * Set the motor and PID values
   */

  public void setMotor() {

    NEO550 = new CANSparkMax(Constants.NEO550_ID, MotorType.kBrushless);

    NEO550.restoreFactoryDefaults();

    NEO550.setSmartCurrentLimit(25);

    m_encoder = NEO550.getEncoder();

    m_encoder.setPosition(0);

    m_pidcontroller = NEO550.getPIDController();

    m_pidcontroller.setP(Constants.kP);
    m_pidcontroller.setI(Constants.kI);
    m_pidcontroller.setD(Constants.kD);
    m_pidcontroller.setIZone(Constants.kIz);
    m_pidcontroller.setFF(Constants.kFF);
    m_pidcontroller.setOutputRange(Constants.kMinOutput, Constants.kMaxOutput);

  }

  //

  public void wheelStopSpinning() {
    NEO550.set(0);
  }

  /**
   * Put PID values on to smartdashboard
   */

  public void pidValues() {
    SmartDashboard.putNumber("ExSM: P Gain", Constants.kP);
    SmartDashboard.putNumber("ExSM: I Gain", Constants.kI);
    SmartDashboard.putNumber("ExSM: D Gain", Constants.kD);
    SmartDashboard.putNumber("ExSM: I Zone", Constants.kIz);
    SmartDashboard.putNumber("ExSM: Feed Forward", Constants.kFF);
    SmartDashboard.putNumber("ExSM: Max Output", Constants.kMaxOutput);
    SmartDashboard.putNumber("ExSM: Min Output", Constants.kMinOutput);

    // double p = SmartDashboard.getNumber("ExSM: P Gain", 0);
    // double i = SmartDashboard.getNumber("ExSM: I Gain", 0);
    // double d = SmartDashboard.getNumber("ExSM: D Gain", 0);
    // double iz = SmartDashboard.getNumber("ExSM: I Zone", 0);
    // double ff = SmartDashboard.getNumber("ExSM: Feed Forward", 0);
    // double max = SmartDashboard.getNumber("ExSM: Max Output", 0);
    // double min = SmartDashboard.getNumber("ExSM: Min Output", 0);
    // double rotation = SmartDashboard.getNumber("set rotation", 0);

    // if ((p != Constants.kP)) {
    // m_pidcontroller.setP(p);
    // Constants.kP = p;
    // }
    // if ((i != Constants.kI)) {
    // m_pidcontroller.setI(i);
    // Constants.kI = i;
    // }
    // if ((d != Constants.kD)) {
    // m_pidcontroller.setD(d);
    // Constants.kD = d;
    // }
    // if ((iz != Constants.kIz)) {
    // m_pidcontroller.setIZone(iz);
    // Constants.kIz = iz;
    // }
    // if ((ff != Constants.kFF)) {
    // m_pidcontroller.setFF(ff);
    // Constants.kFF = ff;
    // }
    // if ((max != Constants.kMaxOutput) || (min != Constants.kMinOutput)) {
    // m_pidcontroller.setOutputRange(min, max);
    // Constants.kMinOutput = min;
    // Constants.kMaxOutput = max;
    // }

  }

  /**
   * Calculate the number of rotations that the control panel spin for stage 1 (rotation)
   * @return
   */

  public double distanceCalculation() {

    m_encoder = NEO550.getEncoder();

    double motorPosition = m_encoder.getPosition();

    double loopSpinning = motorPosition / Constants.gearRatio;

    double loopWheel = loopSpinning / Constants.radiusBigWheel * Constants.radiusSmallWheel;

    SmartDashboard.putNumber("position of the encoder", motorPosition);
    SmartDashboard.putNumber("number of loops", loopSpinning);
    SmartDashboard.putNumber("number of loops of the control panel spinning", loopWheel);

    return loopWheel;

  }

  /**
   * Motor PID position control for stage 1 spinning (rotation)
   */

  private static void PIDadjust() {

    m_pidcontroller = NEO550.getPIDController();

    m_encoder = NEO550.getEncoder();

    m_encoder.setPosition(0);

    m_pidcontroller.setReference(Constants.numberMotorSpinning, ControlType.kPosition);

    SmartDashboard.putNumber("SetPoint", Constants.numberMotorSpinning);

    SmartDashboard.putNumber("ProcessVariable", m_encoder.getPosition());
  }

  /**
   * Motor PID velocity control for stage 2 spinning (color matching)
   */
  
  public void pidVelocityControl() {
  
    m_pidcontroller = NEO550.getPIDController();

    m_encoder = NEO550.getEncoder();

    m_encoder.setPosition(0);
    
    m_pidcontroller.setReference(Constants.motorPositionRPM, ControlType.kVelocity);
    
    SmartDashboard.putNumber("Position: motor RPM", Constants.motorPositionRPM);

    SmartDashboard.putNumber("Position: ProcessVariable", m_encoder.getVelocity());
  }

  public void rotatePanel() {
    PIDadjust();
  }

  /**
   * Get FMS color value
   * @return
   */

  public char getFMS() {
    String gameData;
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    if(gameData.length() > 0)
    {
      switch (gameData.charAt(0))
      {
        case 'B' :
        FMScolor = 'R';
          //Blue case code
          break;
        case 'G' :
        FMScolor = 'Y';
          //Green case code
          break;
        case 'R' :
        FMScolor = 'B';
          //Red case code
          break;
        case 'Y' :
        FMScolor = 'G';
          //Yellow case code
          break;
        default :
          //This is corrupt data
          break;
      }
    }
    return FMScolor;
  }

  /**
   * Set color sensor
   */

  public void setColorSensor() {
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);
   
  }

  /**
   * Match the color value from the color sensor
   */

  public void colorCalibration() {

    final Color detectedColor = m_colorSensor.getColor();

    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

    final double IR = m_colorSensor.getIR();

    final int proximity = m_colorSensor.getProximity();

    if (match.color == kBlueTarget) {
      colorString = 'B';

    } else if (match.color == kRedTarget) {
      colorString = 'R';

    } else if (match.color == kGreenTarget) {
      colorString = 'G';

    } else if (match.color == kYellowTarget) {
      colorString = 'Y';

    } else {
      colorString = 'U';
    }

    SmartDashboard.putNumber("Red", m_colorSensor.getRed());
    SmartDashboard.putNumber("Green", m_colorSensor.getGreen());
    SmartDashboard.putNumber("Blue", m_colorSensor.getBlue());
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", String.valueOf(colorString));
    SmartDashboard.putNumber("Proximity", proximity);
    SmartDashboard.putNumber("IR", IR);

  }

  public void colorTuning(){
    final Color detectedColor = m_colorSensor.getColor();

    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

    final double IR = m_colorSensor.getIR();

    final int proximity = m_colorSensor.getProximity();

    if (match.color == kBlueTarget) {
      colorTesting = 'B';

    } else if (match.color == kRedTarget ) {
      colorTesting = 'R';

    } else if (match.color == kGreenTarget) {
      colorTesting = 'G';

    } else if (match.color == kYellowTarget) {
      colorTesting = 'Y';

    } else {
      colorTesting = 'U';
    }

    SmartDashboard.putNumber("Instataneous Red", m_colorSensor.getRed());
    SmartDashboard.putNumber("Instataneous Green", m_colorSensor.getGreen());
    SmartDashboard.putNumber("Instataneous Blue", m_colorSensor.getBlue());
    SmartDashboard.putNumber("Instataneous Confidence", match.confidence);
    SmartDashboard.putString("Instataneous Detected Color", String.valueOf(colorTesting));
    SmartDashboard.putNumber("Instataneous Proximity", proximity);
    SmartDashboard.putNumber("Instataneous IR", IR);

  }

  /**
   * Get colorString from colorCalibration()
   * @return 
   */

  public char getColorString(){
    
    return colorString;
  }



  @Override
  public void periodic() {

    colorCalibration();
    pidValues();
  }
}
