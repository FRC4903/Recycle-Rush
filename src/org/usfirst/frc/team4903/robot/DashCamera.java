import java.awt.*;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;

//Code credit to Wildcats Team 3540 for source code

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
		p.setBounds(x,y,w,h);
		this.add(p);
		this.ip = ip;
	}
	public void paintComponent(Graphics g){
		Graphics2D canvas = (Graphics2D)g;
		canvas.setColor(Color.white);
		super.paintComponent(g);
		this.setBackground(Color.black);
		
		if(jpg!=null){
			img = jpg.getImage(); //sets frame as bufferedimage
			canvas.drawImage(img,0,0,w,h,this); //paints frame on canvas
		}
	}
	Class JpegInput extends Thread{
		public void run(){
			while (true){
				try{
					HttpURLConnection ul= (HttpURLConnection) new URL(ip).openConnection();
					jpgin = new MjpegInputStream(u1.getInputStream());
					if(p.isVisible()) 			//this refreshes the content pane for each frame being displayed
					p.setVisible(false);

					while(!except){
						try{jpg = jpgin.readMjpegFrame();}  //sets jpg as read Mjpeg frame (new frame from video output)
						catch(IOException e){except = true;}
						repaint();
					}

					jpgin.close();
					p.setVisible(true); //displas frame
				}
				catch (Exception e){
					if(!p.isVisible())
					p.setVisible(true);
					repaint();
				}
			}
		}
	}
	public BufferedImage getImage(){
		return (BufferedImage)img;
	}
}
