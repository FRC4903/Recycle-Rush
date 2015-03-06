import java.awt.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import com.wildcatrobotics.dashboard.objects.UICamera;

public class Dashboard{
	JFrame dash = new JFrame("Dashboard");
	JPanel p = new JPanel();
	
	UICamera camera = new UICamera(5,45,395,300, "Camera", "http://10.35.40.20/mjpg/video.mjpg");
	UICamera camera2 = new UICamera(405,45,395,300, "Camera", "http://10.35.40.21/mjpg/video.mjpg");

	public static void main(String[] args) {
		//System.out.println("Hello World");
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
