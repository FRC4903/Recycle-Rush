//Controls.java

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

public class Controls {
    Data library = new Data();
    double speed_x = library.getRobot().speed_x;
    double speed_y = library.getRobot().speed_y;
    double Left_x = library.getRobot().Left_x;
    double Left_y = library.getRobot().Left_y;
    double Right_x = library.getRobot().Right_x;
    double Right_y = library.getRobot().Right_y;
    double speed = library.getRobot().speed;
    double speed_control = library.getRobot().speed_control;
    boolean tote_up = library.getRobot().tote_up;
    boolean tote_down = library.getRobot().tote_down;
   
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
    		library.getSensor().talon1.set(speed_x);
    		library.getSensor().talon4.set(-speed_y);
    	}
    	else if(Left_x < -0.15 && Left_y > 0.15){ //up - left
    		library.getSensor().talon2.set(-speed_x);
    		library.getSensor().talon3.set(speed_y);
    	} 
    	else if(Left_x < -0.15 && Left_y < -0.15){ //down-left
    		library.getSensor().talon1.set(-speed_x);
    		library.getSensor().talon4.set(speed_y);
    	}
    	else if(Left_x > 0.15 && Left_y < -0.15){ //down right
    		library.getSensor().talon2.set(speed_x);
    		library.getSensor().talon3.set(-speed_y);
    	}
    	
    	if(Right_x != 0){ //rotate rightste
    		talonSet(R_speed_x,R_speed_x,R_speed_x,R_speed_x);
    	}
    }

    public void talonSet(double s1, double s2, double s3, double s4){
		library.getSensor().talon1.set(s1);
		library.getSensor().talon3.set(-s2);
		library.getSensor().talon2.set(s3); //tweak values
		library.getSensor().talon4.set(-s4);
    }

    public void moveTote() {
    	
    	//System.out.println(limit_tote_up.get());
    	//System.out.println(limit_tote_down.get());  // True by default
    	
    	if (tote_up && library.getSensor().limit_tote_up.get()) {
    		speed = 0.2 + 0.5*(1-speed_control);
    		library.getSensor().L1.set(speed);
    		library.getSensor().L2.set(speed);
    		library.getSensor().L3.set(-speed);
    		library.getSensor().L4.set(speed);
    		
    	}
    	else if (tote_down && library.getSensor().limit_tote_down.get()) {
    		speed = -(0.2 + 0.5*(1-speed_control));
    		library.getSensor().L1.set(speed);
    		library.getSensor().L2.set(speed);
    		library.getSensor().L3.set(-speed);
    		library.getSensor().L4.set(speed);
    		
    	}
    	else {
    		library.getSensor().L1.set(0);
    		library.getSensor().L2.set(0);
    		library.getSensor().L3.set(0);
    		library.getSensor().L4.set(0);
    	}
    }

    public void liftToteNum() {
        for (int i=0;i<6;i++){
            if (library.getRobot().getArmValues()[i]){
                speed = i/10;// do the math later
            }
        }
    }

    public void print(String x){
    	System.out.println(x);
    }
    
    public void moveArm() {
    	//System.out.println(limit_arm_in.get());  // True by default
    	//System.out.println(limit_arm_out.get());  // True by default
    	System.out.println(claw_y);
    	
    	if (arm_in && library.getSensor().limit_arm_in.get()) {
    		library.getSensor().CTalon1.set(-0.5);
    	}
    	else if (arm_out && library.getSensor().limit_arm_out.get()) {
    		library.getSensor().CTalon1.set(0.5);
    	}
    	else {
    		library.getSensor().CTalon1.set(0.0);
    	}
    	
    	//System.out.println(limit_rotate_c.get());  // True by default
    	//System.out.println(limit_rotate_cc.get());  // True by default
    	if (claw_y > 0 && claw_safety && library.getSensor().limit_rotate_cc.get()){
    		library.getSensor().CTalon2.set(-claw_y);
    	}
    	else if (claw_y < 0 && claw_safety && library.getSensor().limit_rotate_c.get()) {
    		library.getSensor().CTalon2.set(-claw_y);
    	}
    	else {
    		library.getSensor().CTalon2.set(0.0);
    	}
    }
}