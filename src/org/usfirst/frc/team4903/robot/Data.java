package org.usfirst.frc.team4903.robot;

public class Data{

	private Controls c;
	private Robot r;
	private Sensors s;
	private Auto a;
	public Data(Robot r){
		c = new Controls(this);
		this.r = r;
		s = new Sensors(this);
		a= new Auto(this);
	}
	public Data(){
		
	}
	public Controls getControls(){
		return c;
	}
	public Robot getRobot(){
		return r;
	}
	public Sensors getSensor(){
		return s;
	}
	public Auto getAuto(){
		return a;
	}
}