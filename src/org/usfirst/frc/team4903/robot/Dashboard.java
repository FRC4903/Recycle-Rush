import java.awt.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;


public Class Dashboard{
	JFrame dash = new JFrame("Dashboard");
	JPanel p = new JPanel();
	
	//DashCamera cam1 = new DashCamera(x,y,w,h,name,ip);
	//DashCamera cam2 = new DashCamera(x,y,w,h,name,ip);
	
	public static void main(String[] args) {
		new Dashboard().run();
	}
	public void run(){
		dash.setSize(1024, 710);
		dash.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p.setLayout(null);
		p.setBackground(Color.white);
		p.setSize(dash.getWidth(),dash.getHeight());
		p.setBounds(0,0,dash.getWidth(),dash.getHeight());
		
		dash.add(p);
		dash.setVisible(true);
	}
	public void display(){
		//p.add(cam1);
		//p.add(cam2);
	}
}
