public Data() {
	private Controls c;
	private Robot r;
	private Sensors s;
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