// sensors.java

package org.usfirst.frc.team4903.robot;


import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.CameraServer;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

public class Sensors {
    CANTalon CTalon1 = new CANTalon(1);   //x --> talon ID #1       //this is the extension
    CANTalon CTalon2 = new CANTalon(2);   //y --> talon ID #2       //this is the rotation
    SpeedController talon1 = new Talon(0);
    SpeedController talon2 = new Talon(1);
    SpeedController talon3 = new Talon(2);
    SpeedController talon4 = new Talon(3);
    DigitalInput limit_tote_down = new DigitalInput(0);
    DigitalInput limit_tote_up = new DigitalInput(1);
    DigitalInput limit_rotate_c = new DigitalInput(2);
    DigitalInput limit_rotate_cc = new DigitalInput(3);
    DigitalInput limit_arm_in = new DigitalInput(4);
    DigitalInput limit_arm_out = new DigitalInput(5);
    Victor L1 = new Victor(4);
    Victor L2 = new Victor(5);
    Victor L3 = new Victor(6);
    Victor L4 = new Victor(7);

	public void init_CTalons(CANTalon CTalon) {
	  	CTalon.enableLimitSwitch(true,true);
	   	CTalon.enableBrakeMode(true);
	   	CTalon.setVoltageRampRate(6);
	   	CTalon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
	   	CTalon.reverseOutput(true);
	   	CTalon.reverseSensor(false);
	   	CTalon.setPosition(0);
	   	CTalon.ClearIaccum();
   		CTalon.clearStickyFaults();
	}
}