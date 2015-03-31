package org.usfirst.frc.team4903.robot;

// this class is really messed up
// it really doesn't work with anything else
//it has to go back to the Robot thing after


public class Auto {

    Data library;
    Controls controls;
    Sensors sensors;
    int arm_up, arm_down, arm_range;

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
        int loopNum = 0; // this is the amount of times it takes to loop for 15 seconds
        int loopSec = loopnum / 15; // this is the number of loops per second


        /*
        * mode 1 Pick up yellow tote and container and move to game zone 
        * mode 2 Get containers from land fill zone and move in to game zone before end of auto mode
        */
        
        calibrateArm();

        // Roman put your shit here
        if(mode == 1){
            // Roman's shit

            // gets the yellow tote right at the start
            // tweak the numbers

            for(int i = 0; i < loopNum; i++){
                if(i > 10 && i < 50){
                    // after a 10 loop delay, pick up the tote for 40 loops
                    controls.armUp(20);
                }
                if(i < loopSec*5){
                    // the first 5 seconds it drives forwards
                    controls.talonSet(0.2, -0.2, -0.2, 0.2);
                }
                if(i > loopSec*5){
                    // after 5 seconds it stops
                    controls.talonSet(0, 0, 0, 0);
                }
            }
            controls.talonSet(0, 0, 0, 0);

        }else if(mode == 2){
            boolean container = false;
            for(int i = 0; i < loopNum; i++){
                if(i <  10){
                    controls.armOut(50);
                }
                if(i > 10 && i < 30){
                    moveArmToPos(60, 40);
                }
                if(i > 30 && i < 50){
                    controls.armOut(-30);
                    container = true
                }
                if(container){
                    controls.talonSet(-0.2, 0, 0, 0);   // set this to the right values
                    if(i == 80 || i == 160){ // change these values as needed
                        controls.talonSet(0, 0, 0, 0);
                        container = false;
                    }
                }
            }
        }
    }

    public void teleop(){

    }

    public void moveArmToPos(int percent, int speed){
        int val = arm_down + (int)(arm_range * percent / 100.0);

        if(val > sensors.getEncoderPositionC2()){
            controls.armUp(speed);
        }else if(val < sensors.getEncoderPositionC2()){
            controls.armUp(-speed);
        }
    }

    public void calibrateArm(){

        while (sensors.getLimitCC()) {
            controls.armUp(25);
        }
        arm_up = sensors.getEncoderPositionC2();

        while (sensors.getLimitC()) {
            controls.armUp(-25);
        } 
        arm_down = sensors.getEncoderPositionC2();
        arm_range = Math.abs(arm_up - arm_down);

        while (Math.abs(sensors.getEncoderPositionC2()) != Math.abs(arm_range/2)) {
            controls.armUp(20);
        }
    }
}
