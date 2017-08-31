import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class playGame extends JPanel  implements ActionListener{
	private static final long serialVersionUID = 1L;
	public final int FRAME_WIDTH;
	public final int FRAME_HEIGHT;
	public static int level;

	private int car1Horizontal, car2Horizontal, car3Horizontal, car4Horizontal, car5Horizontal; 
	private int logHorizontal, logVertical, turtleHorizontal, turtleVertical;
	private int numOfLives, frogs, timer, seconds, score, crocTeeth, incNumber, snakeSize;
	private int gameOverWidth, playAgainWidth, gameOverPath, playAgainPath, deathTimer, diveStage, numLevel2, lilyFrogs; 
	private int[] frogArray;
	private double timeBar, timeWidth, jumpSize;
	private boolean isLeft, isRight, isUp, isDown, isGameOver, isTurtleUnder, wasValidJump;
	private boolean[] isLilyFull;
	
	private Graphics2D graphics;
	private Color color1, color2;
	private Timer gameTimer, diveTimer;
	private Font regular, large;
	private String gameString, playAgainString;
	private FontMetrics fm1, fm2;
	private ImageIcon backgroudPic, lifePic;
	private ImageIcon[] frogsPic;
	
	private Car[] car1, car2, car3, car4, car5;
	private Turtle[] turtle;
	private Log[] log;
	private Snake snake;
	private Frog frog;

	public static void main(String[] args){
		new playGame();
	}

	public playGame(){
		level = 1;
		snakeSize = 10;
		numLevel2 = 0;
		diveStage = 1;
		incNumber = 1;
		gameOverPath = -10;
		playAgainPath = -10;
		numOfLives = 3;
		timeBar = 370;
		timeWidth = 200;
		seconds = 47;
		color2 = Color.RED; 
		color1 = Color.GREEN;
		gameTimer = new Timer(50, this);
		diveTimer = new Timer(175, this);

		gameString = "GAMEOVER";
		playAgainString = "Play Again = 1                  Quit = 0";

		regular = new Font("Franklin Gothic Medium", Font.BOLD, 37);
		large = new Font("Franklin Gothic Medium", Font.BOLD, 120);
		fm1 = getFontMetrics(large);
		fm2 = getFontMetrics(regular);
		gameOverWidth = fm1.stringWidth(gameString);
		playAgainWidth = fm2.stringWidth(playAgainString);

		lifePic = new ImageIcon("images/frog\\livesNum3.png");
		backgroudPic = new ImageIcon("images\\gameBoard.png");
		
		FRAME_WIDTH = backgroudPic.getIconWidth();
		FRAME_HEIGHT = backgroudPic.getIconHeight();

		frog = new Frog(FRAME_WIDTH);
		snake = new Snake();
		log = new Log[6];
		turtle = new Turtle[6];
		car1 = new Car[3];
		car2 = new Car[3];
		car3 = new Car[3];
		car4 = new Car[3];
		car5 = new Car[2];

		frogsPic = new ImageIcon[5];
		isLilyFull = new boolean[5];
		frogArray = new int[]{40, 182, 325, 466, 608};

		for (int i = 0; i < frogsPic.length; i++)
			frogsPic[i] = new ImageIcon("");

		int value = 2;

		for (int i = 0; i < turtle.length; i++)
		{
			if (i == 3)
			{
				turtleVertical += 120;
				turtleHorizontal = 100;
				value = 3;
			}

			turtle[i] = new Turtle(new ImageIcon("images/water\\" + value + "turt_0.png"), 50 + turtleHorizontal, 125 + turtleVertical);
			turtleHorizontal += 250;
		}

		for (int i = 0; i < log.length; i++)
		{
			if (i == 2)
			{
				logHorizontal = 200;
				logVertical += 80;
			}
			else if (i == 4)
			{
				logHorizontal = 80;
				logVertical += 40;
			}	

			log[i] = new Log(50 + logHorizontal, 85 + logVertical);
			logHorizontal += 350;
		}

		for (int i = 0; i < car1.length; i++)
		{
			car1[i] = new Car(new ImageIcon("images/car\\carYellow.png"), 150 + car1Horizontal, 485);
			car1Horizontal += 200;

			car2[i] = new Car(new ImageIcon("images/car\\carTractor.png"), 100 + car2Horizontal, 445);
			car2Horizontal += 200;

			car3[i] = new Car(new ImageIcon("images/car\\carRace.png"), 230 + car3Horizontal, 405);
			car3Horizontal += 175;

			car4[i] = new Car(new ImageIcon("images/car\\carWhite.png"), -250 + car4Horizontal, 365);
			car4Horizontal += 200;
		}

		for (int i = 0; i < car5.length; i++)
		{
			car5[i] = new Car(new ImageIcon("images/car\\carTruck.png"), 150 + car5Horizontal, 325);
			car5Horizontal += 300;
		}

		//After all values have been set, start the timer
		gameTimer.start();
		diveTimer.start();

		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		addKeyListener(new frogListener());
		setFocusable(true);
		requestFocus();

		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Frogger");
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setFocusable(false);
		frame.setVisible(true);
	}

	class frogListener extends KeyAdapter{
		public void keyPressed (KeyEvent e){
			switch(e.getKeyCode()) {			//frog controls
				case KeyEvent.VK_DOWN:
					if (frog.getVertical() >= 527)
						frog.setVertical(527);
					else
						isDown = true;
				break;	
				case KeyEvent.VK_LEFT:	
					if (frog.getHorizontal() <= 37 && frog.getVertical() >= 287)
						frog.setHorizontal(37);
					else
						isLeft = true;
				break;
				case KeyEvent.VK_RIGHT:	
					if (frog.getHorizontal() >= 613 && frog.getVertical() >= 287)
						frog.setHorizontal(613);
					else
						isRight = true;
				break;
				case KeyEvent.VK_UP:
					isUp = true;
				break;
			}

			if (level >= 3 || numOfLives <= 0)			//1 or 0 for new game
			{
				if (e.getKeyCode() == KeyEvent.VK_0)
					System.exit(0);
				else if (e.getKeyCode() == KeyEvent.VK_1)
					restart();
			}

		}
	}

	public void actionPerformed(ActionEvent e){

		if(e.getSource() == diveTimer)		//dive stage is 0 to 3 the state of diving in the water
		{
			turtle[0].setImage(new ImageIcon("images/water\\2turt_" + diveStage + ".png"));
			turtle[4].setImage(new ImageIcon("images/water\\3turt_" + diveStage + ".png"));

			if (diveStage >= 5 || diveStage <= 0)
				incNumber = -incNumber;
			if (diveStage >= 4)
				isTurtleUnder = true;
			else
				isTurtleUnder = false;

			diveStage += incNumber;
		}

		else if(e.getSource() == gameTimer)			//frogs gradual movement - hopping
		{
			if(isRight)
			{
				frog.moveRight(FRAME_WIDTH);
				jumpSize++;
			}
			else if(isLeft)
			{
				frog.moveLeft(FRAME_WIDTH);
				jumpSize++;
			}
			else if(isUp)
			{
				frog.moveUp(FRAME_WIDTH);
				jumpSize++;
			}
			else if(isDown)
			{
				frog.moveDown(FRAME_WIDTH);
				jumpSize++;
			}

			if(jumpSize >= 4)
			{
				if(isUp && !frog.getDeath() && numOfLives > 0 && level < 3)
					score+= 10;

				isRight = false;		//movement reset after a jump
				isLeft = false;
				isUp = false;
				isDown = false;

				if(frog.getVertical() <= 47)		//lilys
				{
					for(int i = 0; i < frogArray.length; i++)		//frog entering lilypad sequence
					{
						if (frog.getHorizontal() >= frogArray[i] - 17 && frog.getHorizontal() <= frogArray[i] + 20 && !isLilyFull[i])
						{
							frogsPic[i] = new ImageIcon("images/frog\\frogDown.png");
							isLilyFull[i] = true;
							frog.respawn(FRAME_WIDTH);
							frogs++;

							if (frogs > 0)
								score += 1000;

							score += 50 + (10 * seconds);
							timeBar = 370;
							timeWidth = 200;
							color1 = Color.GREEN;
							seconds = 47;
							timer = 0;
							break;
						}
						else
							death();
					}
				}
				wasValidJump = true;

				if(wasValidJump)
				{
					if(frog.getVertical() <= 247)			//frog entering river sequence
					{
						if ((frog.getVertical() + frog.getHeight() <= log[0].getVertical() + log[0].getHeight() + 5 &&
						frog.getVertical() >= log[0].getVertical() &&
						frog.getHorizontal() + (frog.getWidth()/2) >= log[0].getHorizontal() &&
						frog.getHorizontal() + (frog.getWidth()/2) <= log[0].getHorizontal() + log[0].getWidth() - crocTeeth)
								
						||
						
						(frog.getVertical() + frog.getHeight() <= log[1].getVertical() + log[1].getHeight() + 5 &&
						frog.getVertical() >= log[1].getVertical() &&
						frog.getHorizontal() + (frog.getWidth()/2) >= log[1].getHorizontal() &&
						frog.getHorizontal() + (frog.getWidth()/2) <= log[1].getHorizontal() + log[1].getWidth() - crocTeeth)
						
						||
						
						(frog.getVertical() + frog.getHeight() <= log[2].getVertical() + log[2].getHeight() + 5 &&
						frog.getVertical() >= log[2].getVertical() &&
						frog.getHorizontal() + (frog.getWidth()/2) >= log[2].getHorizontal() &&
						frog.getHorizontal() + (frog.getWidth()/2) <= log[2].getHorizontal() + log[2].getWidth() - crocTeeth)
						
						||
						
						(frog.getVertical() + frog.getHeight() <= log[3].getVertical() + log[3].getHeight() + 5 &&
						frog.getVertical() >= log[3].getVertical() &&
						frog.getHorizontal() + (frog.getWidth()/2) >= log[3].getHorizontal() &&
						frog.getHorizontal() + (frog.getWidth()/2) <= log[3].getHorizontal() + log[3].getWidth() - crocTeeth)
						
						||
						
						(frog.getVertical() + frog.getHeight() <= log[4].getVertical() + log[4].getHeight() + 5 &&
						frog.getVertical() >= log[4].getVertical() &&
						frog.getHorizontal() + (frog.getWidth()/2) >= log[4].getHorizontal() &&
						frog.getHorizontal() + (frog.getWidth()/2) <= log[4].getHorizontal() + log[4].getWidth() - crocTeeth)
						
						||
						
						(frog.getVertical() + frog.getHeight() <= log[5].getVertical() + log[5].getHeight() + 5 &&
						frog.getVertical() >= log[5].getVertical() &&
						frog.getHorizontal() + (frog.getWidth()/2) >= log[5].getHorizontal() &&
						frog.getHorizontal() + (frog.getWidth()/2) <= log[5].getHorizontal() + log[5].getWidth() - crocTeeth)
						
						||
						
						(frog.getVertical() + frog.getHeight() <= turtle[0].getVertical() + turtle[0].getHeight() + 5 &&
						frog.getVertical() >= turtle[0].getVertical() &&
						frog.getHorizontal() + (frog.getWidth()/4) >= turtle[0].getHorizontal() &&
						frog.getHorizontal() + (frog.getWidth()/2)<= turtle[0].getHorizontal() + turtle[0].getWidth())
						
						||
						
						(frog.getVertical() + frog.getHeight() <= turtle[1].getVertical() + turtle[1].getHeight() + 5 &&
						frog.getVertical() >= turtle[1].getVertical() &&
						frog.getHorizontal() + (frog.getWidth()/4) >= turtle[1].getHorizontal() &&
						frog.getHorizontal() + (frog.getWidth()/2)<= turtle[1].getHorizontal() + turtle[1].getWidth())
						
						||
						
						(frog.getVertical() + frog.getHeight() <= turtle[2].getVertical() + turtle[2].getHeight() + 5 &&
						frog.getVertical() >= turtle[2].getVertical() &&
						frog.getHorizontal() + (frog.getWidth()/4) >= turtle[2].getHorizontal() &&
						frog.getHorizontal() + (frog.getWidth()/2)<= turtle[2].getHorizontal() + turtle[2].getWidth())
						
						||
						
						(frog.getVertical() + frog.getHeight() <= turtle[3].getVertical() + turtle[3].getHeight() + 5 &&
						frog.getVertical() >= turtle[3].getVertical() &&
						frog.getHorizontal() + (frog.getWidth()/4) >= turtle[3].getHorizontal() &&
						frog.getHorizontal() + (frog.getWidth()/2)<= turtle[3].getHorizontal() + turtle[3].getWidth())
						
						||
						
						(frog.getVertical() + frog.getHeight() <= turtle[4].getVertical() + turtle[4].getHeight() + 5 &&
						frog.getVertical() >= turtle[4].getVertical() &&
						frog.getHorizontal() + (frog.getWidth()/4) >= turtle[4].getHorizontal() &&
						frog.getHorizontal() + (frog.getWidth()/2)<= turtle[4].getHorizontal() + turtle[4].getWidth())
						
						||
						
						(frog.getVertical() + frog.getHeight() <= turtle[5].getVertical() + turtle[5].getHeight() + 5 &&
						frog.getVertical() >= turtle[5].getVertical() &&
						frog.getHorizontal() + (frog.getWidth()/4) >= turtle[5].getHorizontal() &&
						frog.getHorizontal() + (frog.getWidth()/2)<= turtle[5].getHorizontal() + turtle[5].getWidth()))
						{
							wasValidJump = false;
						}
						else
						{
							death();
							wasValidJump = false;
						}

					}
				}

				jumpSize = 0;
			}

			for(int i = 0; i < 3; i++)			
			{
				car1[i].moveLeft(4 + numLevel2, FRAME_WIDTH);
				car2[i].moveRight(4 + numLevel2, FRAME_WIDTH);
				car3[i].moveLeft(7 + numLevel2, FRAME_WIDTH);
				car4[i].moveRight(5 + numLevel2, FRAME_WIDTH);

				if(frog.getVertical() <= car1[i].getVertical() + car1[i].getHeight() &&
						frog.getVertical() + frog.getHeight() >= car1[i].getVertical() + 5 &&
						frog.getHorizontal() + frog.getWidth() >= car1[i].getHorizontal() &&
						frog.getHorizontal() <= car1[i].getHorizontal() + car1[i].getWidth())
					death();
				else if(frog.getVertical() <= car2[i].getVertical() + car2[i].getHeight() &&
						frog.getVertical() + frog.getHeight() >= car2[i].getVertical() + 5 &&
						frog.getHorizontal() + frog.getWidth() >= car2[i].getHorizontal() &&
						frog.getHorizontal() <= car2[i].getHorizontal() + car2[i].getWidth())
					death();
				else if(frog.getVertical() <= car3[i].getVertical() + car3[i].getHeight() &&
						frog.getVertical() + frog.getHeight() >= car3[i].getVertical() + 5 &&
						frog.getHorizontal() + frog.getWidth() >= car3[i].getHorizontal() &&
						frog.getHorizontal() <= car3[i].getHorizontal() + car3[i].getWidth())
					death();
				else if(frog.getVertical() <= car4[i].getVertical() + car4[i].getHeight() &&
						frog.getVertical() + frog.getHeight() >= car4[i].getVertical() + 5 &&
						frog.getHorizontal() + frog.getWidth() >= car4[i].getHorizontal() &&
						frog.getHorizontal() <= car4[i].getHorizontal() + car4[i].getWidth())
					death();
			}

			for(int i = 0; i < car5.length; i++)
			{
				car5[i].moveLeft(7 + numLevel2, FRAME_WIDTH);

				if(frog.getVertical() <= car5[i].getVertical() + car5[i].getHeight() &&
						frog.getVertical() + frog.getHeight() >= car5[i].getVertical() + 5 &&
						frog.getHorizontal() + frog.getWidth() >= car5[i].getHorizontal() &&
						frog.getHorizontal() <= car5[i].getHorizontal() + car5[i].getWidth())
					death();
			}

			for(int i = 0; i < log.length; i++)
			{
				if(i < 2)
				{
					log[i].move(4 + numLevel2, FRAME_WIDTH);

					if (frog.getVertical() + frog.getHeight() <= log[i].getVertical() + log[i].getHeight() + 5 &&
							frog.getVertical() >= log[i].getVertical() &&
							frog.getHorizontal() + (frog.getWidth()/2) >= log[i].getHorizontal() &&
							frog.getHorizontal() + (frog.getWidth()/2) <= log[i].getHorizontal() + log[i].getWidth())
					{
						if (!frog.getDeath())
							frog.setHorizontal(frog.getHorizontal() + 4 + numLevel2);
					}
				}
				else if(i >= 2 && i < 4)
				{
					log[i].move(5 + numLevel2, FRAME_WIDTH);

					if(frog.getVertical() + frog.getHeight() <= log[i].getVertical() + log[i].getHeight() + 5 &&
							frog.getVertical() >= log[i].getVertical() &&
							frog.getHorizontal() + (frog.getWidth()/2) >= log[i].getHorizontal() &&
							frog.getHorizontal() + (frog.getWidth()/2) <= log[i].getHorizontal() + log[i].getWidth())
					{
						if(!frog.getDeath())
							frog.setHorizontal(frog.getHorizontal() + 5 + numLevel2);
					}
				}
				else if(i >= 4)
				{
					log[i].move(3 + numLevel2, FRAME_WIDTH);

					if(frog.getVertical() + frog.getHeight() <= log[i].getVertical() + log[i].getHeight() + 5 &&
							frog.getVertical() >= log[i].getVertical() &&
							frog.getHorizontal() + (frog.getWidth()/2) >= log[i].getHorizontal() &&
							frog.getHorizontal() + (frog.getWidth()/2) <= log[i].getHorizontal() + log[i].getWidth())
					{
						if(!frog.getDeath())
							frog.setHorizontal(frog.getHorizontal() + 3 + numLevel2);
					}
				}

				if(frog.getVertical() + frog.getHeight() <= turtle[0].getVertical() + turtle[0].getHeight() + 5 &&
						frog.getVertical() >= turtle[0].getVertical() &&
						frog.getHorizontal() + (frog.getWidth()/4) >= turtle[0].getHorizontal() &&
						frog.getHorizontal() + (frog.getWidth()/2) <= turtle[0].getHorizontal() + turtle[0].getWidth()

						||

						(frog.getVertical() + frog.getHeight() <= turtle[4].getVertical() + turtle[4].getHeight() + 5 &&
						frog.getVertical() >= turtle[4].getVertical() &&
						frog.getHorizontal() + (frog.getWidth()/4) >= turtle[4].getHorizontal() &&
						frog.getHorizontal() + (frog.getWidth()/2) <= turtle[4].getHorizontal() + turtle[4].getWidth()))
				{
					if(isTurtleUnder)
						death();
				}

				if(i < 3)
				{
					turtle[i].move(4 + numLevel2, FRAME_WIDTH);

					if(frog.getVertical() + frog.getHeight() <= turtle[i].getVertical() + turtle[i].getHeight() + 5 &&
							frog.getVertical() >= turtle[i].getVertical() &&
							frog.getHorizontal() + (frog.getWidth()/4) >= turtle[i].getHorizontal() &&
							frog.getHorizontal() + (frog.getWidth()/2) <= turtle[i].getHorizontal() + turtle[i].getWidth())
					{
						if(!frog.getDeath())
							frog.setHorizontal(frog.getHorizontal() - 4 - numLevel2);
					}
				}
				else
				{
					turtle[i].move(3 + numLevel2, FRAME_WIDTH);

					if(frog.getVertical() + frog.getHeight() <= turtle[i].getVertical() + turtle[i].getHeight() + 5 &&
							frog.getVertical() >= turtle[i].getVertical() &&
							frog.getHorizontal() + (frog.getWidth()/4) >= turtle[i].getHorizontal() &&
							frog.getHorizontal() + (frog.getWidth()/2) <= turtle[i].getHorizontal() + turtle[i].getWidth())
					{
						if(!frog.getDeath())
							frog.setHorizontal(frog.getHorizontal() - 3 - numLevel2);
					}
				}

			}

			if((frog.getVertical() <= 247) && (frog.getHorizontal() + frog.getWidth() >= FRAME_WIDTH || frog.getHorizontal() <= 0))
				death();

			snake.move(snakeSize + numLevel2, FRAME_WIDTH);

			if(frog.getVertical() <= snake.getVertical() + snake.getHeight() &&
					frog.getVertical() + frog.getHeight() >= snake.getVertical() + 5 &&
					frog.getHorizontal() + frog.getWidth() >= snake.getHorizontal() &&
					frog.getHorizontal() <= snake.getHorizontal() + snake.getWidth())
				death();

			if(numOfLives > 0 && level < 3 )			//represents the timer bar
			{
				timeBar += .2;
				timeWidth -= .2;
			}

			timer += 50;

			if(timer >= 1000)
			{
				seconds--;
				timer = 0;
			}

			if(seconds <= 0)
			{
				seconds = 0;
				timer = 0;
			}

			if(timeWidth <= 125 && timeWidth > 50)			//timer turns yello and then red as it expires
				color1 = Color.YELLOW;
			else if(timeWidth <= 50)
				color1 = Color.RED;

			if(timeWidth <= 10)
			{
				timeWidth = 10;
				timeBar = 560;
				death();
			}

			if(frog.getDeath() && numOfLives > 0 && level < 3)
			{
				deathTimer++;

				if(deathTimer >= 25)
				{
					frog.respawn(FRAME_WIDTH);
					deathTimer = 0;
					seconds = 47;
					timer = 0;
					numOfLives--;
					color1 = Color.GREEN;

					if(numOfLives > 0)
					{
						timeBar = 370;
						timeWidth = 200;
					}

					lifePic = new ImageIcon("images/frog\\livesNum" + numOfLives + ".png");
				}
			}

			if(numOfLives <= 0)
			{
				isGameOver = true;
				frog.setImage(new ImageIcon(""));
			}

			if(isGameOver)
			{
				gameOverWidth = fm1.stringWidth(gameString);
				gameOverPath += 10;

				if(gameOverPath >= 330)
				{
					gameOverPath = 330;
					playAgainPath = 360;
				}

			}

			if(frogs >= 5)				//new level sequence
			{
				for(int i = 0; i < frogsPic.length; i++)
				{
					frogsPic[i] = new ImageIcon("");
					isLilyFull[i] = false;
				}

				lilyFrogs++;
				{
					if(lilyFrogs >= 5)
					{
						numLevel2 = 3;
						frogs = 0;
						crocTeeth = 80;
						level++;
						lilyFrogs = 0;

						for(int i = 0; i < log.length; i++)
							log[i].setLevel2(true);
					}
				}
			}

			if(level >= 3)
			{
				color2 = Color.GREEN;
				gameString = "YOU WIN!";
				gameOverWidth = fm2.stringWidth(gameString);
				isGameOver = true;
				frog.setDeath(true);
				frog.setImage(new ImageIcon(""));
			}

			repaint();
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.drawImage(backgroudPic.getImage(), 0, 0, this);
		graphics.drawImage(lifePic.getImage(), 255, 587, this);
		graphics.setFont(regular);
		graphics.setColor(Color.GREEN);
		graphics.drawString(Integer.toString(score), 155, 609);
		graphics.setColor(color1);
		graphics.fill(new RoundRectangle2D.Double(timeBar, 585, timeWidth, 25, 10, 10));

		for(int i = 0; i < frogsPic.length; i++)
			graphics.drawImage(frogsPic[i].getImage(), frogArray[i], 43, this);
		for(int i = 0; i < turtle.length; i++)
			turtle[i].drawTurtle(graphics);
		for(int i = 0; i < log.length; i++)
			log[i].drawLog(graphics);
		for(int i = 0; i < car1.length; i++)
		{
			car1[i].drawVehicle(graphics);
			car2[i].drawVehicle(graphics);
			car3[i].drawVehicle(graphics);
			car4[i].drawVehicle(graphics);
		}
		for(int i = 0; i < car5.length; i++)
			car5[i].drawVehicle(graphics);
		
		snake.drawSnake(graphics);
		frog.drawFrog(graphics);

		graphics.setFont(large);
		graphics.setColor(color2);
		graphics.drawString(gameString,  getWidth() / 2 - gameOverWidth / 2, gameOverPath);
		graphics.setFont(regular);
		graphics.drawString(playAgainString, getWidth() / 2 - playAgainWidth / 2, playAgainPath);
	}

	public void death(){
		frog.die();
	}

	public void restart(){
		frog.setImage(new ImageIcon("images/frog\\frogUp.png"));
		frog.setDeath(false);
		frog.setVertical(527);
		frog.setHorizontal(FRAME_WIDTH/2 - frog.getWidth()/2);

		for(int i = 0; i < log.length; i++)
			log[i].setLevel2(false);
		for (int i = 0; i < frogsPic.length; i++)
		{
			frogsPic[i] = new ImageIcon("");
			isLilyFull[i] = false;
		}

		isGameOver = false;

		gameString = "GAMEOVER";
		color2 = Color.RED;
		color1 = Color.GREEN;

		numLevel2 = 0;
		level = 1;
		gameOverPath = -10;
		playAgainPath = -10;
		crocTeeth = 0;
		score = 0;
		frogs = 0;

		numOfLives = 3;
		timeBar = 370;
		timeWidth = 200;
		seconds = 47;

		lifePic = new ImageIcon("images/frog\\livesNum" + numOfLives + ".png");
	}
}