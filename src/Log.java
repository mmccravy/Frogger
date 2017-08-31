import java.awt.*;
import javax.swing.*;

public class Log{

	private int horizontal, vertical, width, height;
	private ImageIcon logPic;
	private boolean isLevel2;

	public Log(int hor, int vert){
		this.logPic = new ImageIcon("images/water\\log.png");
		this.width = logPic.getIconWidth();
		this.height = logPic.getIconHeight();
		this.horizontal = hor;
		this.vertical = vert;
		this.isLevel2 = false;
	}
	//setters
	public void setHeight(int h){
		this.height = h;
	}
	
	public void setWidth(int w){
		this.width = w;
	}

	public void setSize(int w, int h){
		this.width = w;
		this.height = h;
	}

	public void setLocation(int x, int y){
		this.horizontal = x;
		this.vertical = y;
	}
	
	public void setHorizontal(int x){
		this.horizontal = x;
	}

	public void setVertical(int y){
		this.vertical = y;
	}

	public void setImage(ImageIcon image){
		this.logPic = image;
	}

	public void setLevel2 (boolean n){
		this.isLevel2 = n;
	}
	//getters

	public int getHeight(){
		return this.height;
	}
	
	public int getWidth(){
		return this.width;
	}

	public int getHorizontal(){
		return this.horizontal;
	}

	public int getVertical(){
		return this.vertical;
	}

	public ImageIcon getImage(){
		return this.logPic;
	}

	public boolean getLevel2(){
		return this.isLevel2;
	}
	//move
	public void move(int pixels, int FRAME_WIDTH)
	{
		this.horizontal += pixels;

		if (horizontal >= FRAME_WIDTH)
		{
			horizontal = 0 - width;
			if (isLevel2 && horizontal <= 0) 
				this.logPic = new ImageIcon("images/water\\logCroc.png");
		}

		if (!isLevel2)
			this.logPic = new ImageIcon("images/water\\log.png");
	}
	//draw
	public void drawLog (Graphics2D graphic){
		graphic.drawImage(logPic.getImage(), horizontal, vertical, width, height, null);
	}
}