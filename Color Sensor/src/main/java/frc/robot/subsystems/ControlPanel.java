/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
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


public class ControlPanel extends SubsystemBase {
  /**
   * Creates a new ControlPanal.
   */
  final I2C.Port i2cPort = I2C.Port.kOnboard;

  final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  
  final ColorMatch m_colorMatcher = new ColorMatch();

  final Color detectedColor = m_colorSensor.getColor(); 
    
  ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

  final double IR = m_colorSensor.getIR();
 
  final int proximity = m_colorSensor.getProximity();

  final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
   
  
  String colorString;

  public ControlPanel() {
   

  }

  public void colorCalibration() {

    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);
    SmartDashboard.putNumber("Proximity", proximity);
    SmartDashboard.putNumber("IR", IR);

  }

  public void colorVerification(){

    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);

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
  }
  

  @Override
  public void periodic() {
  
    colorCalibration();
    colorVerification();

  }
}
