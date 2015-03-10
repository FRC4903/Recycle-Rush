package org.usfirst.frc.team4903.robot;

// this class is really messed up
// it really doesn't work with anything else
//it has to go back to the Robot thing after


public class Auto {

    Data library = new Data ();
    int arm_up, arm_down, arm_range;

    //                NO FUCKING MAGIC NUMBERS!!!!!
    int max_length=100;                     //THIS NEEDS TO CHANGE
    int length_robot =25;                   //THIS NEEDS TO CHANGE

    public Auto(){
        
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
