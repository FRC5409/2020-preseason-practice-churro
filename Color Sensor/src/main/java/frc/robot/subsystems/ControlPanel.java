/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
//import edu.wpi.first.wpilibj.GenericHID;
//import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
import com.revrobotics.ColorSensorV3;
//import com.revrobotics.ColorSensorV3.RawColor;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class ControlPanel extends SubsystemBase {
  /**
   * Creates a new ControlPanal.
   */
  public I2C.Port i2cPort = I2C.Port.kOnboard;

  public ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  
  public ColorMatch m_colorMatcher = new ColorMatch();

  public Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  public Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  public Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  public Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
  
  public String colorString = "";

  private CANSparkMax NEO550;

  public ControlPanel() {
   
    setColorSensor();
    spinningMotor();

  }

  public void spinningMotor() {

    NEO550 = new CANSparkMax (Constants.NEO550_ID, MotorType.kBrushless);
   
    NEO550.restoreFactoryDefaults();

   }

   public void wheelSpinning(){
    NEO550.set(0.5);
   }

   public void wheelNotSpinning(){
    NEO550.set(0);
   }

  public void setColorSensor(){
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);
  }

  public void colorCalibration() {
    
    final Color detectedColor = m_colorSensor.getColor(); 
    
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

    final double IR = m_colorSensor.getIR();
 
    final int proximity = m_colorSensor.getProximity();

    if (match.color == kBlueTarget) {
      colorString = "Blue";
  
    } else if (match.color == kRedTarget) {
      colorString = "Red";
  
    } else if (match.color == kGreenTarget) {
      colorString = "Green";
 
    } else if (match.color == kYellowTarget) {
      colorString = "Yellow";
 
    } else {
      colorString = "Unknown";
    }

    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);
    SmartDashboard.putNumber("Proximity", proximity);
    SmartDashboard.putNumber("IR", IR);

  }
  

  @Override
  public void periodic() {
  
    colorCalibration();

  }
}
