import java.awt.Graphics2D;
import javax.swing.ImageIcon;

public class Frog{

	private int horizontal, vertical, width, height;
	private ImageIcon frogPic;
	private boolean isDead;

	public Frog(int FRAME_WIDTH){
		frogPic = new ImageIcon("images/frog\\frogUp.png");
		width = frogPic.getIconWidth();
		height = frogPic.getIconHeight();
		horizontal = FRAME_WIDTH/2 - width/2;
		vertical = 527;
		isDead = false;
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

	public void setHorizontal(int x){
		this.horizontal = x;
	}

	public void setVertical(int y){
		this.vertical = y;
	}

	public void setLocation(int x, int y){
		this.horizontal = x;
		this.vertical = y;
	}

	public void setImage(ImageIcon image){
		this.frogPic = image;
	}

	public void setDeath (boolean death){
		this.isDead = death;
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

	public boolean getDeath(){
		return this.isDead;
	}
	
	public ImageIcon getImage(){
		return this.frogPic;
	}
	//death
	public void die(){
		this.frogPic = new ImageIcon("images/frog\\death.png");
		this.isDead = true;
	}
	//Movement functions
	public void moveLeft(int FRAME_WIDTH){
		if (!this.isDead)
		{
			this.horizontal -= 12;
			this.frogPic = new ImageIcon("images/frog\\frogLeft.png");
		}
	}
	
	public void moveDown(int FRAME_WIDTH){
		if (!this.isDead)
		{
			this.vertical += 10;
			this.frogPic = new ImageIcon("images/frog\\frogDown.png");
		}
	}

	public void moveRight(int FRAME_WIDTH){
		if (!this.isDead)
		{
			this.horizontal += 12;
			this.frogPic = new ImageIcon("images/frog\\frogRight.png");
		}
	}

	public void moveUp(int FRAME_WIDTH){
		if (!this.isDead)
		{
			this.vertical -= 10;
			this.frogPic = new ImageIcon("images/frog\\frogUp.png");
		}
	}
	
	public void respawn(int FRAME_WIDTH){
		this.isDead = false;
		this.horizontal = FRAME_WIDTH/2 - width/2;
		this.vertical = 527;
		this.frogPic = new ImageIcon("images/frog\\frogUp.png");
	}
	//draw
	public void drawFrog(Graphics2D graphic)
	{
		graphic.drawImage(frogPic.getImage(), horizontal, vertical, width, height, null);
	}
}