public Data() {
	Controls c;
	Robot r;
	Sensors s;
	public Data(){
		c = new Controls ();
		r = new Robot ();
		s = new Sensors ();
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
}