import javax.swing.*;
import java.awt.*;

public class Car{
	
	private int horizontal, vertical, width, height;
	private ImageIcon carPic;

	public Car(ImageIcon carPic, int xPos, int yPos){
		this.carPic = carPic;
		this.horizontal = xPos;
		this.vertical = yPos;
		width = carPic.getIconWidth();
		height = carPic.getIconHeight();
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
		this.carPic = image;
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
		return this.carPic;
	}
	//move functions
	public void moveRight(int pixels, int FRAME_WIDTH){
		this.horizontal += pixels;
		if (horizontal >= FRAME_WIDTH + width)
			horizontal = 0 - width;
	}

	public void moveLeft(int pixels, int FRAME_WIDTH){
		this.horizontal -= pixels;
		if (horizontal <= 0 - width)
			horizontal = FRAME_WIDTH;
	}
	//draw
	public void drawVehicle (Graphics2D graphic){
		graphic.drawImage(carPic.getImage(), horizontal, vertical, width, height, null);
	}
}