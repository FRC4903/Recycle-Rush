package org.usfirst.frc.team4903.robot;

public class Auto {

    Data library;
    Controls controls;
    Sensors sensors;
    int arm_up, arm_down, arm_range;

    int max_length=100;
    int length_robot =25;

    public Auto(Data d){
        library = d;
        controls = library.getControls();
        sensors = library.getSensor();
    }

    public void autonomous() {
    	int mode = 1;
        int loopNum = 75247; // this is the amount of times it takes to loop for 15 seconds
        int loopSec = loopNum / 15; // this is the number of loops per second


        /*
         * mode 1 Pick up yellow tote and container and move to game zone 
         * mode 2 Get containers from land fill zone and move in to game zone before end of auto mode
         */

        if(mode == 1){
            // gets the yellow tote right at the start

            for (int i = 0; i < loopSec*2; i++) {
                controls.pickUpTote(50);
            }
            for (int i = 0; i < loopSec*5; i++) {
                controls.talonSet(0.2, -0.2, -0.2, 0.2);
            }
            for (int i = 0; i < loopSec*2; i++) {
                controls.pickUpTote(40);
            }
            for (int i = 0; i < loopSec*1; i++) {
                controls.talonSet(0.1, -0.1, -0.1, 0.1);
            }
            for (int i = 0; i < loopSec*5; i++) {
                controls.talonSet(0.0, 0.0, 0.0, 0.0);
            }

            controls.talonSet(0, 0, 0, 0);

        } else if(mode == 2){
            boolean container = true;
            for(int i = 0; i < loopNum; i++){
                if(i <  10){
                    //controls.armOut(50);
                }
                if(i > 10 && i < 30){
                    //moveArmToPos(60, 40);
                }
                if(i > 30 && i < 50){
                    //controls.armOut(-30);
                    //container = true;
                }
                if(container){
                    controls.talonSet(-0.25, 0.25, 0, 0);
                    if(i > loopSec * 5){
                        controls.talonSet(0, 0, 0, 0);
                        container = false;
                    }
                }
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

    public void cPickUpArm(){
        // stands for container retract arm

        while (arm_range != Math.abs(sensors.getEncoderPositionC1())) {
            //this just sets it to the middle, need to figure the exact value with expermientation
            controls.armUp(2);  
        }
        while ((max_length - length_robot) != sensors.getEncoderPositionC2()) {
            controls.armOut(2);
        }
        while (sensors.getLimitCC()) {
            controls.armUp(-2);
        }
        while ((max_length - length_robot) != sensors.getEncoderPositionC2()) {
            controls.armOut(-2);
        }
    }

}
