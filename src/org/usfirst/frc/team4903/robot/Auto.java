public void autonomous() {
    int mode = 1;
    /*
    * mode 1 Pick up yellow tote and container and move to game zone 
    * mode 2 Get containers from land fill zone and move in to game zone before end of auto mode
    */
    
    // calibrate arm
    while (limit_rotate_cc.get()) {
    	CTalon1.set(-0.2);
    }
    encoder_start = CTalon1.getEncPosition();
    while (limit_rotate_c.get()) {
    	CTalon1.set(0.2);
    } 
    encoder_end = CTalon1.getEncPosition();
    encoder_difference = Math.abs(encoder_end - encoder_start);
    while (Math.abs(CTalon1.getEncPosition()) != Math.abs(encoder_difference/2)) {
    	CTalon1.set(-0.2);
    }
    // roman put your shit here
}