package org.usfirst.frc.team4903.robot;

// this class is really messed up
// it really doesn't work with anything else
//it has to go back to the Robot thing after


public class Auto {

    Data library;
    Controls controls;
    Sensors sensors;
    int arm_up, arm_down, arm_range, tote_low, tote_high, tote_range;

    //                NO FUCKING MAGIC NUMBERS!!!!!
    int max_length=100;                     //THIS NEEDS TO CHANGE
    int length_robot =25;                   //THIS NEEDS TO CHANGE

    public Auto(Data d){
        library = d;
        controls = library.getControls();
        sensors = library.getSensor();
    }

    public void autonomous() {
        int mode = 1;
        /*
        * mode 1 Pick up yellow tote and container and move to game zone 
        * mode 2 Get containers from land fill zone and move in to game zone before end of auto mode
        */
        
        calibrateArm();
        calibrateToteLifter();

        // Roman put your shit here
        if(mode == 1){
            // Roman's shit

            // gets the yellow tote right at the start
            // tweak the numbers
            for(int i = 0; i < 1000; i++){
                controls.armUp()
            }

            for(int i = 0; i < 100000000; i++){
                talonSet(1.0, 1.0, 1.0, 1.0);
            }

        }else if(mode == 2){
            cPickUpArm();

            //turn around
            
            while (sensors.getLimitC()) {
                controls.armUp(-0.25);
            }
        }
    }

    public void moveArmToPos(int percent, int speed){
        int val = arm_down + (int)(arm_range / 100.0 * percent);

        if(val > sensors.getEncoderPositionC2()){
            controls.armUp(speed);
        }else if(val < sensors.getEncoderPositionC2()){
            controls.armUp(-speed);
        }
    }

    public void calibrateArm(){

        while (sensors.getLimitCC()) {
            controls.armUp(0.25);
        }
        arm_up = sensors.getEncoderPositionC2();

        while (sensors.getLimitC()) {
            controls.armUp(-0.25);
        } 
        arm_down = sensors.getEncoderPositionC2();
        arm_range = Math.abs(arm_up - arm_down);

        while (Math.abs(sensors.getEncoderPositionC2()) != Math.abs(arm_range/2)) {
            controls.armUp(0.2);
        }
    }

    public void cPickUpArm(){
        // stands for container retract arm

        //This assumes you are going to be hooked to the container
        //Then it moves it up, and brings it and drops it in front and goes all the way down.
        while (arm_range != Math.abs(sensors.getEncoderPositionC1())) {
            //this just sets it to the middle, need to figure the exact value with expermientation
            controls.armUp(0.2);  
        }
        while ((max_length - length_robot) != sensors.getEncoderPositionC2()) {
            controls.armOut(0.2);
        }
        while (sensors.getLimitCC()) {
            controls.armUp(-0.2);
        }
        while ((max_length - length_robot) != sensors.getEncoderPositionC2()) {// fix this
            controls.armOut(-0.2);
        }
    }

}
