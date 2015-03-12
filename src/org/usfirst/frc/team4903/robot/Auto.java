package org.usfirst.frc.team4903.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CameraServer;



public class Auto {

    Data library;
    Controls control;
    Sensors sensors;
    int arm_up, arm_down, arm_range;
    Joystick baseControl, armControl;

    double right_x;
    double right_y;
    double left_x;
    double left_y;
    double speed_x;
    double speed_y;
   
    double speed_control;
    double claw_y;

    boolean tote_up;
    boolean tote_down;
    boolean arm_out;
    boolean arm_in;
    boolean claw_safety;

    boolean arm7;
    boolean arm8;
    boolean arm9;
    boolean arm10;
    boolean arm11;
    boolean arm12;

    boolean cam_change;

    double deceleration = 0.01; // move to getValues method

    

    //                NO FUCKING MAGIC NUMBERS!!!!!
    int max_length=100;                     //THIS NEEDS TO CHANGE
    int length_robot =25;                   //THIS NEEDS TO CHANGE

    public Auto(Data dat){
        library = dat;
        control = library.getControls();
        sensors = library.getSensor();
    }

    public void autonomous() {
        int mode = 1;
        /*
        * mode 1 Pick up yellow tote and container and move to game zone 
        * mode 2 Get containers from land fill zone and move in to game zone before end of auto mode
        */
        
        calibrateArm();

        // Roman put your shit here
        if(mode == 1){
            // Roman's shit

            // gets the yellow tote right at the start
            library.getControls().pickUpTote(50);       // need pickUpTote method that takes % of motor power as a parameter
                                                        // -ve value makes tote arm go down, +ve value makes it go up

        }
    }

    public void operatorControl(){

        while (isOperatorControl() && isEnabled()) {

            // gets variables from the sensor and controls class
            getVariables();


            //  vision shit
            if (cam_change){
                server = CameraServer.getInstance();
                server.setQuality(50);   
                server.startAutomaticCapture("cam1");
            }else{
                server = CameraServer.getInstance();
                server.setQuality(50);
                server.startAutomaticCapture("cam0");
            }  

            // drive controls
            if (right_x > 0){
                speed_x += right_x;
            }
            if (right_x < 0){
                speed_x -= right_x;
            }
            
            if (left_y == 0 && speed_y > 0) {
                speed_y -= deceleration;
            }       
            if (left_x == 0 && speed_x > 0) {
                speed_x -= deceleration;
            }

            // send all the variables to controls so they can do shit
            control.moveBase();
            control.moveTote();
            control.moveArm();
            Timer.delay(0.02);
            
        }
    }

    public void getVariables(){
        left_x = baseControl.getRawAxis(0);
        left_y = baseControl.getRawAxis(1);
        right_x = baseControl.getRawAxis(4);
        right_y = baseControl.getRawAxis(5);
        speed_control = armControl.getRawAxis(3);
        claw_y = armControl.getRawAxis(1);

        speed_x = left_x/2.0; 
        speed_y = left_y/2.0;
        rotate_speed = right_x/2.0;

        claw_safety = armControl.getRawButton(1);
        //  change this as needed
        tote_up = armControl.getRawButton(5);
        tote_down = armControl.getRawButton(3);
        arm_out = armControl.getRawButton(6);
        arm_in = armControl.getRawButton(4);

        arm7 = armControl.getRawButton(7);
        arm8 = armControl.getRawButton(8);
        arm9 = armControl.getRawButton(9);
        arm10 = armControl.getRawButton(10);
        arm11 = armControl.getRawButton(11);
        arm12 = armControl.getRawButton(12);

        cam_change = true;
    }


    // rewrite this shit

    public void calibrateArm(){
        Controls control = library.getControls();
        Sensors sensors = library.getSensor();

        while (sensors.getLimitCC()) {
            control.armUp(25);                          // need armUp method that takes % of motor power as a parameter
                                                        // -ve value makes arm go down, +ve value makes it go up
        }
        arm_up = sensors.getEncoderPositionC2();        // need this method, gets encoder value on CTalon2

        while (sensors.getLimitC()) {
            control.armUp(-25);
        } 
        arm_down = sensors.getEncoderPositionC2();
        arm_range = Math.abs(arm_up - arm_down);

        while (Math.abs(sensors.getEncoderPositionC2()) != Math.abs(arm_range/2)) {
            control.armUp(20);
        }
        control.armUp(0);
    }

    public void retractArm(){
        //This assumes you are going to be hooked to the container
        //Then it moves it up, and brings it and drops it in front and goes all the way down.
        while (arm_range != Math.abs(library.getSensor().getEncoderPositionC1())) {
                //this just sets it to the middle, need to figure the exact value with expermientation
               library.getControls().armUp(20);  
        }
        while ((max_length - length_robot) != library.getSensor().getEncoderPositionC2()) {
            library.getControls().armOut(20);
        }
        while (library.getSensor().getLimitCC()) {
            library.getControls().armUp(-20);
        }
    }
}
