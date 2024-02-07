
package frc.robot;

// General Imports
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
// Imports for motors
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController; // Probaly won't need this library it is being discontiued.
import com.revrobotics.RelativeEncoder;
// Imports for smart dashboard
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// Imports for Camera
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.cameraserver.CameraServer;
import java.security.Key;
// Imports for NavX
import com.kauailabs.navx.frc.AHRS;
// Shuffleboard Variables 


//encoders?
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWM;



public class Robot extends TimedRobot { 

  private final String kDefaultAuto
  private final CANSparkMax frontLeft = new CANSparkMax(8, MotorType.kBrushless); //CAN ID is set on the sparkmax 
  private final CANSparkMax backLeft = new CANSparkMax(1, MotorType.kBrushless); // Motortype must also be set for the neo it is brushless.
  private final CANSparkMax frontRight = new CANSparkMax(2, MotorType.kBrushless);
  private final CANSparkMax backRight = new CANSparkMax(3, MotorType.kBrushless);
  // In our code for last year we used motor control groups. That has been discontinued. We now must use 'follow' to link the two motors.
  private final DifferentialDrive yangMobile = new DifferentialDrive(frontLeft,frontRight); // Differential drive is the main object to control the drivetrain.
  // For now we have one controller created for a driver.
  private final XboxController driver = new XboxController(0);

  //encoder??
  double diameter = 6/12; //6 inch wheels
  double dist = 0.5*3.14/1024; //feet per pulse

  @Override
  public void robotInit() {
    // In order to use four motors for the drivetrain each side must follow the motor which is contolled by arcadeDrive. 
    backRight.follow(frontRight); //Back motors follow; front motors are controlled.
    backLeft.follow(frontLeft);
    // For differential drive to work the right side must be inverted
    frontRight.setInverted(true);
    backRight.setInverted(true); // note Mr.O is not sure if the back motor needs to be inverted. Experiment if there is time.

  }
  @Override
  public void robotPeriodic() {

  }
  public void autonomousInit() {
    m_timer.reset();
    m_timer.starter();
    //go backwards
    frontLeft.setInverted(true);
    frontRight.setInverted(false);
    
    m_autoSelected = m_chooser.getSelected();
   // for smart dashboard 
    m_autoSelected = SmartDashboard.getString("Auto Seletor ", kDefaultAuto ) ;
    System.out.println( "Auto selected:" + m_autoSelected) ; 


  @Override
  public void autonomousPeriodic() {
    if (m_timer.get() >0) && (m_timer.get() <5){ 
      //THE FIVE SECONDS ARE CHANGEABLE
      yangMobile.arcadeDrive (0.5, 0.0, isAutonomous());
    else {yangMobile.stopMotor();}
      }
  }
  @Override
  public void teleopInit() {

  }
  @Override
  public void teleopPeriodic() { 
    //The drivetrain is controlled by arcadedrive in teleopPeriodic. 
    yangMobile.arcadeDrive(-driver.getLeftY(), -driver.getRightX());
    // As it is now the left joystick up and down (y axis) controls acceleration.
    // The right joystick left and right (x axis) controls turning.
  }
}
