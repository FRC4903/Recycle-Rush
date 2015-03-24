// sensors.java

package org.usfirst.frc.team4903.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;

//              WTF is this????

// this is not adequate in the slightest
// variables are private, use getter and setter methods to access them

//              FIX THIS SHIT UP!!

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
    Data library;
    public Sensors(Data d){
    	library=d;
    }
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
	
	public boolean getToteLimitUp(){
		return limit_tote_up;
	}
	public boolean getToteLimitDown(){
		return limit_tote_down;
	}
	public boolean getLimitCC() {
		return limit_rotate_cc.get();
	}
	public boolean getLimitC() {
		return limit_rotate_c.get();
	}
	public int getEncoderPositionC2() {
		return CTalon2.getEncPosition();
	}
	public int getEncoderPositionC1() {
		return CTalon1.getEncPosition();
	}
}