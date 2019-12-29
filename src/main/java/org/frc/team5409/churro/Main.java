package org.frc.team5409.churro;

import edu.wpi.first.wpilibj.RobotBase;

import org.frc.team5409.churro.control.Services;

public final class Main {
  private Main() {
  }

  /**
   * Main initialization function. Do not perform any initialization here.
   *
   * <p>If you change your main robot class, change the parameter type.
   */
  public static void main(String... args) {
    Services.start();
    
    RobotBase.startRobot(Robot::new);
  }
}
