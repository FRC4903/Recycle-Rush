
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
//import edu.wpi.first.wpilibj.CameraServer;

import java.util.*;
/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
    RobotDrive myRobot;
    Joystick baseControl,clawControl;
    Ultrasonic ultra;
    CANTalon CTalon1 = new CANTalon(1);   //x --> talon ID #1
    CANTalon CTalon2 = new CANTalon(2);   //y --> talon ID #2
    SpeedController talon1 = new Talon(0);
    SpeedController talon2 = new Talon(1);
    SpeedController talon3 = new Talon(2);
    SpeedController talon4 = new Talon(3); 
    Victor L1 = new Victor(4);
    Victor L2 = new Victor(5);
    Victor L3 = new Victor(6);
    Victor L4 = new Victor(7);
    
    double Right_x;
    double Right_y;
    double Left_x;
    double Left_y;
   
    double speed_x;
	double speed_y;
	double R_speed_x;
	double R_speed_y;
	
	boolean tote_up;
	boolean tote_down;
	boolean arm_out;
	boolean arm_in;
	boolean claw_safety;
	
	double speed;
	
	double claw_y;
	boolean isTopMoving = false;
    public Robot() {
        //myRobot = new RobotDrive(0, 1, 2, 3);
        //myRobot.setExpiration(0.1);
        baseControl = new Joystick(1);
        clawControl = new Joystick(0);
        //ultra = new Ultrasonic(0,1);
    }
    public void robotInit(){
    	CTalon1.enableLimitSwitch(true,true);
    	CTalon1.enableBrakeMode(true);
    	CTalon1.setVoltageRampRate(6);
    	CTalon1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	CTalon1.reverseOutput(true);
    	CTalon1.reverseSensor(false);
    	CTalon1.setPosition(0);
    	CTalon1.ClearIaccum();
    	CTalon1.clearStickyFaults();
    	
    	CTalon2.enableLimitSwitch(true,true);
    	CTalon2.enableBrakeMode(true);
    	CTalon2.setVoltageRampRate(6);
    	CTalon2.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	CTalon2.reverseOutput(true);
    	CTalon2.reverseSensor(false);
    	CTalon2.setPosition(0);
    	CTalon2.ClearIaccum();
    	CTalon2.clearStickyFaults();
    }
    /**
     * Drive left & right motors for 2 seconds then stop
     */
    public void autonomous() {
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
        //myRobot.setSafetyEnabled(true);
    	
    	double deceleration = 0.01;
    //	double acceleration = 0.03;
    	
    	//double rotate;
        while (isOperatorControl() && isEnabled()) {
        	Left_x = baseControl.getRawAxis(0);
        	Left_y = baseControl.getRawAxis(1);
        	Right_x = baseControl.getRawAxis(4);
        	Right_y = baseControl.getRawAxis(5);
        	
        	speed_x = Left_x; 
        	speed_y = Left_y;
        	R_speed_x = Right_x;
        	R_speed_y = Right_y;
        	
        	claw_y = clawControl.getRawAxis(1);
        	claw_safety = clawControl.getRawButton(1);
        	tote_up = clawControl.getRawButton(5);
        	tote_down = clawControl.getRawButton(3);
        	arm_out = clawControl.getRawButton(6);
        	arm_in = clawControl.getRawButton(4);
        	
        	if (Right_x>0) {
        		speed_x += Right_x;
        	}
        	if (Right_x<0) {
        		speed_x -= Right_x;
        	}
        	
        	if (Left_y == 0 && speed_y > 0) {
        		speed_y -= deceleration;
        	}		
        	if (Left_x == 0 && speed_x > 0) {
        		speed_x -= deceleration;
        	}
        	moveBase();
        	moveTote();
        	moveArm();

        	//rotate = (Right_x * Right_x + Right_y * Right_y);
        	
/*        	if (Left_y == 0 && speed_y > 0) {
        		speed_y -= deceleration;
        	}
        	if (Left_x == 0 && speed_x > 0) {
        		speed_x -= deceleration;
        	}
        	 
        	if (Left_y == 1 && speed_y < 0) {
        		speed_y -= acceleration;
        	}
        	if (Left_y == -1 && speed_y > 0){
         		speed_y += acceleration;
        	
        	}
        	if (Left_x == 1 && speed_x < 0) {
        		speed_x -= acceleration;
        	}*/
        	//myRobot.mecanumDrive_Polar(speed_y, speed_x, Right_x);
        	Timer.delay(0.02);
        }
    }
    public void moveBase(){
    	if (Left_x <= 0.15  && Left_x >= -0.15){ //doesnt require the joystick to be perfectly parallel to the x or y axis, so controlling is easier
    		if(Left_y != 0){ //foreward
    			talonSet(-speed_y,speed_y,speed_y,-speed_y);
    		}
    	}
    	else if(Left_y <= 0.15 && Left_y >= -0.15){
    		if(Left_x != 0){ //right
    			talonSet(speed_x,speed_x,speed_x,speed_x);
    		}
    	}
    	else if(Left_x > 0.15 && Left_y > 0.15){ //up - right
    		talon1.set(speed_x);
    		talon4.set(-speed_y);
    	}
    	else if(Left_x < -0.15 && Left_y > 0.15){ //up - left
    		talon2.set(-speed_x);
    		talon3.set(speed_y);
    	} 
    	else if(Left_x < -0.15 && Left_y < -0.15){ //down-left
    		talon1.set(-speed_x);
    		talon4.set(speed_y);
    	}
    	else if(Left_x > 0.15 && Left_y < -0.15){ //down right
    		talon2.set(speed_x);
    		talon3.set(-speed_y);
    	}
    	if(/*Left_x >= -0.15 && Left_x <= 0.15 && Left_y >= -0.15 && Left_y <= 0.15 &&*/ Right_x != 0){ //rotate rightste
    		System.out.println(true);
    		talonSet(R_speed_x,-R_speed_x,R_speed_x,-R_speed_x);
    	}
    }
    public void talonSet(double s1, double s2, double s3, double s4){
		talon1.set(s1);
		talon3.set(-s2);
		talon2.set(s3); //tweak values
		talon4.set(-s4);
    }
    
    public void moveTote() {
    	if (tote_up) {
    		speed = 0.5;
    		L1.set(speed);
    		L2.set(speed);
    		L3.set(-speed);
    		L4.set(speed);
    		
    	}
    	else if (tote_down) {
    		speed = -0.5;
    		L1.set(speed);
    		L2.set(speed);
    		L3.set(-speed);
    		L4.set(speed);
    		
    	}
    	else {
    		L1.set(0);
    		L2.set(0);
    		L3.set(0);
    		L4.set(0);
    	}
    	
    	if (claw_y != 0 && claw_safety) {
    		CTalon2.set(-claw_y);
    	}
    	else {
    		CTalon2.set(0.0);
    	}
    }
    
    public void moveArm() {
    	if (arm_in) {
    		CTalon1.set(-0.5);
    	}
    	else if (arm_out) {
    		CTalon1.set(0.5);
    	}
    	else {
    		CTalon1.set(0.0);
    	}
    }

    /**
     * Runs during test mode
     */
    public void test() {
    }
}
