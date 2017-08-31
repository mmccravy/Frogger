import java.awt.Graphics2D;
import javax.swing.ImageIcon;

public class Turtle{

	private int horizontal, vertical, width, height;
	private ImageIcon turtlePic;

	public Turtle(ImageIcon turtlePic, int horizontal, int vertical){	
		this.horizontal = horizontal;
		this.vertical = vertical;
		this.turtlePic = turtlePic;
		this.width = turtlePic.getIconWidth();
		this.height = turtlePic.getIconHeight();
	}
	//setters
	public void setHeight(int height){
		this.height = height;
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public void setHorizontal(int x){
		horizontal = x;
	}
	
	public void setVertical(int y){
		vertical = y;
	}
	
	public void setImage(ImageIcon turtle){
		this.turtlePic = turtle;
	}
	//getters
	public int getHeight(){	
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHorizontal(){
		return horizontal;
	}
	
	public int getVertical(){
		return vertical;
	}
	
	public ImageIcon getImage(){
		return turtlePic;
	}
	//move
	public void move(int pixels, int FRAME_WIDTH){
		horizontal -= pixels;
		if(horizontal + width <= 0)
			horizontal = FRAME_WIDTH;
	}
	//draw
	public void drawTurtle(Graphics2D graphic){
		graphic.drawImage(turtlePic.getImage(), horizontal, vertical, null);
	}

}