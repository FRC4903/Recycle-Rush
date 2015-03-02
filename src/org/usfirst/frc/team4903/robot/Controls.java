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

public Class Controls{
	Sensors sensor;

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
    		sensor.talon1.set(speed_x);
    		sensor.talon4.set(-speed_y);
    	}
    	else if(Left_x < -0.15 && Left_y > 0.15){ //up - left
    		sensor.talon2.set(-speed_x);
    		sensor.talon3.set(speed_y);
    	} 
    	else if(Left_x < -0.15 && Left_y < -0.15){ //down-left
    		sensor.talon1.set(-speed_x);
    		sensor.talon4.set(speed_y);
    	}
    	else if(Left_x > 0.15 && Left_y < -0.15){ //down right
    		sensor.talon2.set(speed_x);
    		sensor.talon3.set(-speed_y);
    	}
    	
    	if(Right_x != 0){ //rotate rightste
    		talonSet(R_speed_x,R_speed_x,R_speed_x,R_speed_x);
    	}
    }

    public void talonSet(double s1, double s2, double s3, double s4){
		sensor.talon1.set(s1);
		sensor.talon3.set(-s2);
		sensor.talon2.set(s3); //tweak values
		sensor.talon4.set(-s4);
    }

    public void moveTote() {
    	
    	//System.out.println(limit_tote_up.get());
    	//System.out.println(limit_tote_down.get());  // True by default
    	
    	if (tote_up && sensor.limit_tote_up.get()) {
    		speed = 0.2 + 0.5*(1-speed_control);
    		sensor.L1.set(speed);
    		sensor.L2.set(speed);
    		sensor.L3.set(-speed);
    		sensor.L4.set(speed);
    		
    	}
    	else if (tote_down && sensor.limit_tote_down.get()) {
    		speed = -(0.2 + 0.5*(1-speed_control));
    		sensor.L1.set(speed);
    		sensor.L2.set(speed);
    		sensor.L3.set(-speed);
    		sensor.L4.set(speed);
    		
    	}
    	else {
    		sensor.L1.set(0);
    		sensor.L2.set(0);
    		sensor.L3.set(0);
    		sensor.L4.set(0);
    	}
    }

    public void print(String x){
    	System.out.println(x);
    }
    
    public void moveArm() {
    	//System.out.println(limit_arm_in.get());  // True by default
    	//System.out.println(limit_arm_out.get());  // True by default
    	System.out.println(claw_y);
    	
    	if (arm_in && sensor.limit_arm_in.get()) {
    		sensor.CTalon1.set(-0.5);
    	}
    	else if (arm_out && sensor.limit_arm_out.get()) {
    		sensor.CTalon1.set(0.5);
    	}
    	else {
    		sensor.CTalon1.set(0.0);
    	}
    	
    	//System.out.println(limit_rotate_c.get());  // True by default
    	//System.out.println(limit_rotate_cc.get());  // True by default
    	if (claw_y > 0 && claw_safety && sensor.limit_rotate_cc.get()){
    		sensor.CTalon2.set(-claw_y);
    	}
    	else if (claw_y < 0 && claw_safety && sensor.limit_rotate_c.get()) {
    		sensor.CTalon2.set(-claw_y);
    	}
    	else {
    		sensor.CTalon2.set(0.0);
    	}
    }
}