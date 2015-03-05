import java.awt.*;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;


public Class DashCamera{
	Jpanel p = new Jpanel();
	Image img = null;
	String ip;
	MjpegFrame jpg;
	MjpegInputStream jpgin;
	boolean except = false;

	public DashCamera (int x, int y, int w, int h, String name, String ip){
		super(x,y,w,h,name);
		this.setLayout(null);
		p.setLayout(null);
		//p.setBounds(dimensions in here later)
		this.add(p);
		this.ip = ip;
	}
	public void paintComponent(Graphics g){

	}
	Class JpegInput extends Thread{
		public void run(){
			while (true){
				try{
					//jpgin = new MjpegInputStream(ip address camera input stream goes here)
					if(cp.isVisible())
					cp.setVisible(false);

					while(!except){
						try{jpg = jpgin.readMjpegFrame();}
						catch(IOException e){except = true;}
						repaint();
					}

					jpgin.close();
					p.setVisible(true);
				}
				catch (Exception e){
					if(!cp.isVisible())
					cp.setVisible(true);
					repaint();
				}
			}
		}
	}
	public BufferedImage getImage(){

	}

}