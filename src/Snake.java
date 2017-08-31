import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class Snake{

	private int horizontal, vertical, width, height, direction;
	private int[] snakePath;
	private ImageIcon snakePic;
	private Random random;

	public Snake(){
		random = new Random();
		snakePath = new int[] {85, 125, 165, 205, 245, 280};
		direction = 1;
		
		snakePic = new ImageIcon("images/water\\snake" + direction + ".png");
		width = snakePic.getIconWidth();
		height = snakePic.getIconHeight();
		horizontal = -width;
		vertical = 280;
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
		this.snakePic = image;
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
		return this.snakePic;
	}
	//move
	public void move(int pixels, int FRAME_WIDTH){
		if (direction == 0)
			this.horizontal -= pixels;
		else
			this.horizontal += pixels;

		if (horizontal <= 0 - width || horizontal >= FRAME_WIDTH + width)
		{
			direction = random.nextInt(2);
			vertical = snakePath[random.nextInt(1) + 4];
			
			if (direction == 0)
				horizontal = FRAME_WIDTH;
			else
				horizontal = 0 - width;
			
			setImage(new ImageIcon("images/water\\snake" + direction + ".png"));
		}
	}
	//draw
	public void drawSnake (Graphics2D graphic)
	{
		graphic.drawImage(snakePic.getImage(), horizontal, vertical, width, height, null);
	}
}