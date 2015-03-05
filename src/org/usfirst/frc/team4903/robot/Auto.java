// this class is really messed up
// it really doesn't work with anything else
//it has to go back to the Robot thing after


public Auto(){
    Data library = new Data ();
    int encoder_end,encoder_start,encoder_difference;
    int max_length=100;                     //THIS NEEDS TO CHANGE
    int length_robot =25;                   //THIS NEEDS TO CHANGE
    public void autonomous() {
        int mode = 1;
        /*
        * mode 1 Pick up yellow tote and container and move to game zone 
        * mode 2 Get containers from land fill zone and move in to game zone before end of auto mode
        */
        
        // calibrate arm
        while (limit_rotate_cc.get()) {
            CTalon2.set(-0.2);
        }
        encoder_start = CTalon1.getEncPosition();
        while (limit_rotate_c.get()) {
            CTalon2.set(0.2);
        } 
        encoder_end = CTalon2.getEncPosition();
        encoder_difference = Math.abs(encoder_end - encoder_start);
        while (Math.abs(CTalon2.getEncPosition()) != Math.abs(encoder_difference/2)) {
            CTalon2.set(-0.2);
        }
        // roman put your shit here
    }   
    public void retractArm(){
        //This assumes you are going to be hooked to the container
        //Then it moves it up, and brings it and drops it in front and goes all the way down.
        while (Math.abs(encoder_difference/2) != Math.abs(library.getSensor().CTalon1.getEncPosition())){       //this just setsit to the middle, need to figure the exact value with expermientation
               library.getSensor().CTalon2.set(0.2);  
        }
        while ((max_length - length_robot) != library.getSensor().CTalon2.getEncPosition()){
            library.getSensor().CTalon1.set(-0.2);
        }
        while (library.getSensor().limit_rotate_cc.get()){
            library.getSensor().CTalon2.set(-0.2)
        }
    }
}
