
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


// this code needs to be re-worked over the march break
// srrsly, it sucks
// not even going to try to fix


public class Robot extends SampleRobot {
    RobotDrive myRobot;
    Data library = new Data();
    Controls control;
    Joystick baseControl,clawControl;
    Ultrasonic ultra;
    CameraServer server;
    
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

    boolean arm7;
    boolean arm8;
    boolean arm9;
    boolean arm10;
    boolean arm11;
    boolean arm12;
	
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
    public boolean [] getArmValues(){
        return new boolean[]  {arm7,arm8,arm9,arm10,arm11,arm12};
    }
    public void robotInit(){
        library.getSensor().init_CTalons(library.getSensor().CTalon1);
        library.getSensor().init_CTalons(library.getSensor().CTalon2);
    	
    	// Camera stuff
    
    	//frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    	
    	//session = NIVision.IMAQdxOpenCamera("cam0",NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        //NIVision.IMAQdxConfigureGrab(session);
    }
    /**
     * Drive left & right motors for 2 seconds then stop
     */
    public void autonomous() {
    	while (library.getSensor().limit_rotate_cc.get()) {
    		library.getSensor().CTalon1.set(-0.2);
    	}
    	encoder_start = library.getSensor().CTalon1.getEncPosition();
    	while (library.getSensor().limit_rotate_c.get()) {
    		library.getSensor().CTalon1.set(0.2);
    	}
    	encoder_end = library.getSensor().CTalon1.getEncPosition();
    	encoder_difference = Math.abs(encoder_end - encoder_start);
    	while (Math.abs(library.getSensor().CTalon1.getEncPosition()) != Math.abs(encoder_difference/2)) {
    		library.getSensor().CTalon1.set(-0.2);
    	}
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
        //myRobot.setSafetyEnabled(true);
        //double acceleration = 0.03;
    	//double rotate;
    	//NIVision.IMAQdxStartAcquisition(session);
    	//NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);
        double deceleration = 0.01;
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
            arm7 = clawControl.getRawButton(7);
            arm8 = clawControl.getRawButton(8);
            arm9 = clawControl.getRawButton(9);
            arm10 = clawControl.getRawButton(10);
            arm11 = clawControl.getRawButton(11);
            arm12 = clawControl.getRawButton(12);

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

        	control.moveBase();
        	control.moveTote();
        	control.moveArm();
        	Timer.delay(0.02);
        	
        }
        //NIVision.IMAQdxStopAcquisition(session);
    }

    /**
     * Runs during test mode
     */
    public void test() {
    }
}