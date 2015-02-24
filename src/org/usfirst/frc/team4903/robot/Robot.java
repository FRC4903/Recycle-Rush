
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
    Sensors sensor;
    Joystick baseControl,clawControl;
    Ultrasonic ultra;
    CANTalon CTalon1 = new CANTalon(1);   //x --> talon ID #1
    CANTalon CTalon2 = new CANTalon(2);   //y --> talon ID #2
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
    CameraServer server;
    CameraServer server2;
    
    double Right_x;
    double Right_y;
    double Left_x;
    double Left_y;
   
    double speed_x;
	double speed_y;
	double R_speed_x;
	double R_speed_y;
	
	int encoder_start;
	int encoder_end;
	int encoder_difference;
	
	boolean tote_up;
	boolean tote_down;
	boolean arm_out;
	boolean arm_in;
	boolean claw_safety;
	boolean cam_change;
	
	double speed;
	
	double claw_y;
	double speed_control;
	
	int session;
    Image frame;
    
    
    public Robot() {
        //myRobot = new RobotDrive(0, 1, 2, 3);
        //myRobot.setExpiration(0.1);
        baseControl = new Joystick(1);
        clawControl = new Joystick(0);
        //ultra = new Ultrasonic(0,1);
    }
    public void robotInit(){
        sensor.init_CTalons(CTalon1);
        sensor.init_CTalons(CTalon2);
    	
    	// Camera stuff
    
    	//frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    	
    	//session = NIVision.IMAQdxOpenCamera("cam0",NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        //NIVision.IMAQdxConfigureGrab(session);
    }
    /**
     * Drive left & right motors for 2 seconds then stop
     */
    public void autonomous() {
    	while (limit_rotate_cc.get()) {
    		CTalon1.set(-0.2);
    	}
    	encoder_start = CTalon1.getEncPosition();
    	while (limit_rotate_c.get()) {
    		CTalon1.set(0.2);
    	}
    	encoder_end = CTalon1.getEncPosition();
    	encoder_difference = Math.abs(encoder_end - encoder_start);
    	while (Math.abs(CTalon1.getEncPosition()) != Math.abs(encoder_difference/2)) {
    		CTalon1.set(-0.2);
    	}
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
        //myRobot.setSafetyEnabled(true);
    	
    	double deceleration = 0.01;
    //	double acceleration = 0.03;
    	
    	//double rotate;
    	
    	//NIVision.IMAQdxStartAcquisition(session);
    	//NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);
    	
        while (isOperatorControl() && isEnabled()) {
        	//System.out.println(CTalon1.getEncPosition());
        	//System.out.print(" " + CTalon2.getEncPosition());
        	
        	Left_x = baseControl.getRawAxis(0);
        	Left_y = baseControl.getRawAxis(1);
        	Right_x = baseControl.getRawAxis(4);
        	Right_y = baseControl.getRawAxis(5);
        	speed_control = clawControl.getRawAxis(3);
        	claw_y = clawControl.getRawAxis(1);
        	
        	speed_x = Left_x/2.0; 
        	speed_y = Left_y/2.0;
        	R_speed_x = Right_x/2.0;
        	
        	claw_safety = clawControl.getRawButton(1);
        	tote_up = clawControl.getRawButton(5);
        	tote_down = clawControl.getRawButton(3);
        	arm_out = clawControl.getRawButton(6);
        	arm_in = clawControl.getRawButton(4);
        	cam_change = clawControl.getRawButton(2);

        	if (cam_change){
            	server = CameraServer.getInstance();
            	server.setQuality(50);   
        		server.startAutomaticCapture("cam1");
        	}
        	else{
        		server = CameraServer.getInstance();
        		server.setQuality(50);
        		server.startAutomaticCapture("cam0");  // Change to can0
        	}
        	
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
        	Timer.delay(0.02);
        	
        }
        //NIVision.IMAQdxStopAcquisition(session);
    }
    public void moveBase(){
    	if (Left_x <= 0.15  && Left_x >= -0.15){ //doesnt require the joystick to be perfectly parallel to the x or y axis, so controlling is easier
    		if(Left_y != 0){ 
    			talonSet(-speed_y,speed_y,speed_y,-speed_y); //(neg, neg, pos, pos) foreward or (pos,pos,neg,neg) back
    		}
    	}
    	else if(Left_y <= 0.15 && Left_y >= -0.15){
    		if(Left_x != 0){ 
    			talonSet(speed_x,-speed_x,speed_x,-speed_x); // right (neg, pos, neg, pos) left (pos,neg,pos,neg)
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
    	
    	if(Right_x != 0){ //rotate rightste
    	//	System.out.println(true);
    		talonSet(R_speed_x,R_speed_x,R_speed_x,R_speed_x);
    	}
/*    	if(Left_x != 0 && Left_y != 0){
        	talon1.set(0);
        	talon2.set(0);
        	talon3.set(0);
        	talon4.set(0);
    	}*/
    }
    public void talonSet(double s1, double s2, double s3, double s4){
		talon1.set(s1);
		talon3.set(-s2);
		talon2.set(s3); //tweak values
		talon4.set(-s4);
    }
    
    public void moveTote() {
    	
    	//System.out.println(limit_tote_up.get());
    	//System.out.println(limit_tote_down.get());  // True by default
    	
    	if (tote_up && limit_tote_up.get()) {
    		speed = 0.2 + 0.5*(1-speed_control);
    		L1.set(speed);
    		L2.set(speed);
    		L3.set(-speed);
    		L4.set(speed);
    		
    	}
    	else if (tote_down && limit_tote_down.get()) {
    		speed = -(0.2 + 0.5*(1-speed_control));
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
    }
    public void print(String x){
    	System.out.println(x);
    }
    public void moveArm() {
    	//System.out.println(limit_arm_in.get());  // True by default
    	//System.out.println(limit_arm_out.get());  // True by default
    	System.out.println(claw_y);
    	
    	if (arm_in && limit_arm_in.get()) {
    		CTalon1.set(-0.5);
    	}
    	else if (arm_out && limit_arm_out.get()) {
    		CTalon1.set(0.5);
    	}
    	else {
    		CTalon1.set(0.0);
    	}
    	
    	//System.out.println(limit_rotate_c.get());  // True by default
    	//System.out.println(limit_rotate_cc.get());  // True by default
    	if (claw_y > 0 && claw_safety && limit_rotate_cc.get()){
    		CTalon2.set(-claw_y);
    	}
    	else if (claw_y < 0 && claw_safety && limit_rotate_c.get()) {
    		CTalon2.set(-claw_y);
    	}
    	else {
    		CTalon2.set(0.0);
    	}
    }

    /**
     * Runs during test mode
     */
    public void test() {
    }
}