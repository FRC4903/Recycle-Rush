//Controls.java

package org.usfirst.frc.team4903.robot;

public class Controls {
    Data library;

    // change this
    //  there should not be a getRobot method in the data class. It does not need one

    double speed_x;
    double speed_y;
    double Left_x;
    double Left_y;
    double Right_x;
    double Right_y;
    double speed;
    double speed_control;
    double claw_y;
    double R_speed_x;
    double R_speed_y;
    double s1;
    double s2;
    double s3;
    double s4;
    
    boolean claw_safety;
    boolean arm_in;
    boolean arm_out;
    boolean tote_up;
    boolean tote_down;
    
    public Controls(Data d) {
        library= d;
    }
    
    public void updateVar() {
        speed_x = library.getRobot().speed_x;
        speed_y = library.getRobot().speed_y;
        Left_x = library.getRobot().Left_x;
        Left_y = library.getRobot().Left_y;
        Right_x = library.getRobot().Right_x;
        Right_y = library.getRobot().Right_y;
        speed = library.getRobot().speed;
        speed_control = library.getRobot().speed_control;
        claw_y = library.getRobot().claw_y;
        R_speed_x = library.getRobot().R_speed_x;
        R_speed_y = library.getRobot().R_speed_y;
        
        claw_safety = library.getRobot().claw_safety;
        arm_in = library.getRobot().arm_in;
        arm_out = library.getRobot().arm_out;
        tote_up = library.getRobot().tote_up;
        tote_down = library.getRobot().tote_down;
    }
   
    public void moveBase(){
        updateVar();

        s1 += -speed_y;
        s2 += speed_y;
        s3 += speed_y;
        s4 += -speed_y;
        
        s1 += speed_x;
        s2 += -speed_x;
        s3 += speed_x;
        s4 += -speed_x;
    
        s1 += R_speed_x;
        s2 += R_speed_x;
        s3 += R_speed_x;
        s4 += R_speed_x;

        talonSet(s1, s2, s3, s4)

        s1 = 0;
        s2 = 0;
        s3 = 0;
        s4 = 0;

        /*
        if(Left_y != 0){
            talonSet(-speed_y,speed_y,speed_y,-speed_y); //(neg, neg, pos, pos) foreward or (pos,pos,neg,neg) back
        }
        
        if(Left_x != 0){ 
            talonSet(speed_x,-speed_x,speed_x,-speed_x); // right (neg, pos, neg, pos) left (pos,neg,pos,neg)
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
        */
    }

    public void talonSet(double s1, double s2, double s3, double s4){
        library.getSensor().talon1.set(s1);
        library.getSensor().talon3.set(-s2);
        library.getSensor().talon2.set(s3); 
        library.getSensor().talon4.set(-s4);
    }

    public void moveTote() {
        updateVar();
        
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
        updateVar();
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
        updateVar();
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

    // the speed variables are percents of max speed
    public void armUp(int speed) {
        updateVar();
        if (llibrary.getSensor().getLimitCC() == false && library.getSensor().getLimitC()== false){
            library.getSensor().CTalon2.set(speed/100);
        }
        
        
    }
    public void armOut(int speed) {
        updateVar();
        if (llibrary.getSensor().getArmIn() == false && library.getSensor().getArmOut()== false){
            library.getSensor().CTalon1.set(speed/100);
        }
    }
    public void pickUpTote(int s) {
        updateVar();
        speed=s/100;
        moveTote();
    }
}