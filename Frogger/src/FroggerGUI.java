import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FroggerGUI extends JFrame implements KeyListener, ActionListener {

	//Class fields (the objects found in the game)
	public static JFrame GUI = new JFrame();
	public static Frog frog = new Frog();
	public static Vehicle[] vehicle = new Vehicle[12];
	public static Log[] log = new Log[9];
	public static Turtle[] turtle = new Turtle[8];
	public static JLabel[] cave = new JLabel[5];
	public static JLabel[] lives = new JLabel[5];
	public static JLabel lblScore = new JLabel ("SCORE: 00");
	public static JLabel[] flies = new JLabel[5];
	public static Snake snake = new Snake();

	//Static variables (to be accessed from other classes)
	public static int score;
	public static int count = 120;
	public static int cntFirstMove = 0;

	//Constants for frame width and height
	public static final int FRAME_WIDTH = 700;
	public static final int FRAME_HEIGHT = 800;

	//Instance fields
	private JLabel lblLives = new JLabel ("LIVES:"); //Label next to the frog lives images
	private JLabel lblCount = new JLabel ("TIME: 120"); //Label to display the time (the game clock)
	private Timer countDownTimer = new Timer (1000, this); //The game timer
	private Timer flyTimer = new Timer (3000, this); //The timer to show the flies
	private int cntFlyTimer = 1; //Counts the fly timer ticks
	private int flyIndex1; //The index of the first random fly
	private int flyIndex2; //The index of the second random fly
	private JLabel lblTime = new JLabel(); //This is a timer bar that shrinks in width to visually represent the time
	private int cntShrink = 240; //This is the width of the timer bar
	private Font font = new Font ("Calibri", Font.BOLD, 24); //This is the font for the labels

	//Constructor method
	public FroggerGUI() {

		//Call other methods
		frameSetup();
		gameLabelSetup();
		gameObjectsSetup();

	}

	private void gameLabelSetup() {

		//Loops through each of the lives and creates an image for each one
		for (int x = 0; x < lives.length; x++) {

			lives[x] = new JLabel();
			lives[x].setBounds(65 + 25 * x, 739, 30, 30);
			lives[x].setIcon(new ImageIcon(new ImageIcon("images/frog10.gif").getImage().getScaledInstance(30, 30, 0)));
			GUI.add(lives[x]);

		}

		//Creates the lives label
		lblLives.setBounds(5, 730, 60, 50);
		lblLives.setFont(font);
		lblLives.setForeground(Color.RED);
		GUI.add(lblLives);

		//Creates the score label
		lblScore.setBounds(10, 2, 170, 50);
		lblScore.setFont(font);
		lblScore.setForeground(Color.RED);
		GUI.add(lblScore);

		//Creates the time label (game clock)
		lblCount.setBounds(585, 2, 120, 50);
		lblCount.setFont(font);
		lblCount.setForeground(Color.RED);
		GUI.add(lblCount);

		//Creates a shrinking time bar to visually show the time
		lblTime.setBounds(225, 10, cntShrink, 35);
		lblTime.setOpaque(true);
		lblTime.setBackground(Color.GREEN);
		GUI.add(lblTime);

	}

	private void gameObjectsSetup() {

		//Creates the frog object
		frog.setBounds(frog.getX(), frog.getY(), 40, 40);
		GUI.add(frog);

		//Loops through each fly to preset the image of a fly in each cave
		for (int x = 0; x < flies.length; x++) {

			flies[x] = new JLabel();
			flies[x].setBounds(30 + 150 * x, 85, 35, 35);
			flies[x].setIcon(new ImageIcon(new ImageIcon("images/fly.gif").getImage().getScaledInstance(35, 35, 0)));
			flies[x].setVisible(false);
			GUI.add(flies[x]);

		}

		//Loops through each vehicle and sets up the direction, speed, location (x, y), file name and hit box (x, y)
		for (int x = 0 ; x < vehicle.length ; x++) {

			if (x < 3)
				vehicle[x] = new Vehicle(1, 16, 200 + 150 * (3-x), 625, "yellowCar", 50, 50);
			else if (x < 6)	
				vehicle[x] = new Vehicle(-1, 28, -300 + 150 * (6-x), 575, "dozer", 50, 50);
			else if (x < 9)
				vehicle[x] = new Vehicle(1, 24, 100 + 150 * (9-x), 525, "purpleCar", 50, 50);
			else if (x < 10)
				vehicle[x] = new Vehicle(-1, 8, 50 + 150 * (10-x), 475, "greenCar", 50, 50);
			else if (x < 12)
				vehicle[x] = new Vehicle(1, 32, 100 + 300 * (12-x), 425, "truck", 100, 50);

			vehicle[x].setBounds(vehicle[x].getStartX(),vehicle[x].getStartY(),vehicle[x].getWidth(),vehicle[x].getHeight());
			GUI.add(vehicle[x]);
		}

		//Loops through each turtle and sets up the number of turtles, maximum dive state, location (x, y), file name and hit box (x, y)
		for (int x = 0 ; x < turtle.length ; x++) {

			if (x < 4) {

				turtle[x] = new Turtle (3, 1, 200 * (4 - x), 325, "turtles30", 150, 50);

				if (x == 2) 

					turtle[x].setMaxDive(4);

			} else if (x < 8) {

				turtle[x] = new Turtle (2, 1, 175 * (8 - x), 175, "turtles20", 100, 50);

				if (x == 7)

					turtle[x].setMaxDive(4);
			}

			turtle[x].setBounds(turtle[x].getStartX(),turtle[x].getStartY(),turtle[x].getWidth(),turtle[x].getHeight());
			GUI.add(turtle[x]);
		}

		//Loops through each log and sets up the speed, location (x, y), file name and hit box (x, y)
		for (int x = 0; x < log.length ; x++) {

			if (x < 3)
				log[x] = new Log (8, -200 + 300 * (3 - x), 275, "logShort", 125, 50);		
			else if (x < 6)
				log[x] = new Log (4, -300 + 400 * (6 - x), 225, "logLong", 280, 50);
			else if (x < 9)
				log[x] = new Log (6, -200 + 300 * (9 - x), 125, "logMedium", 175, 50);

			log[x].setBounds(log[x].getStartX(),log[x].getStartY(),log[x].getWidth(),log[x].getHeight());
			GUI.add(log[x]);

		}

		//Loops through each cave to create the object of a cave
		for (int x = 0 ; x < cave.length ; x++) {

			cave[x] = new JLabel();
			cave[x].setBounds(27 + x * 150, 80, 43, 50);
			GUI.add(cave[x]);

		}

		//Creates the snake object
		snake.setBounds(200, 385, 100, 35);
		GUI.add(snake);

	}

	//This method sets up the frame
	private void frameSetup() {

		GUI.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setResizable(false);
		GUI.setLocationRelativeTo(null);
		GUI.setLayout(null);
		GUI.setContentPane(new JLabel (new ImageIcon(new ImageIcon("images/background.gif").getImage().getScaledInstance(FRAME_WIDTH,FRAME_HEIGHT,0))));
		GUI.addKeyListener(this);
		GUI.setVisible(true);
	}

	//Getters for the score
	public static int getScore() {

		return score;

	}

	//Setter for the score
	public static void setScore(int score) {

		FroggerGUI.score = score;

	}

	//This method sets the score label to the current score
	public static String scoreToString() {

		return "SCORE: " + score;

	}

	//This method sets the time label to the current time
	public String timeToString() {

		return "TIME: " + count;

	}

	//This method allows input from the keys of a keyboard
	public void keyPressed(KeyEvent event) {

		//Checks if an arrow key has been pressed
		if (event.getKeyCode() >= 37 && event.getKeyCode() <= 40) {

			//Lets the frog.jump method to know which arrow key has been pressed
			frog.jump(event.getKeyCode());

			//Checks if it's the first time the frog jumps, if it is, start the timers and increment the move counter by 1
			if (cntFirstMove == 0) {

				countDownTimer.start();
				flyTimer.start();
				cntFirstMove++;

			}

		}

	}

	//This method implements the action listener
	public void actionPerformed(ActionEvent event) {

		//Checks if the source was the count down timer
		if (event.getSource() == countDownTimer) {

			//Decrease the count by 1 and update the time label
			count--;
			lblCount.setText(timeToString());

			//Increase the score by 10 and update the score label
			score +=10;
			lblScore.setText(scoreToString());

			//Decrease the width of the bar by 2
			cntShrink -= 2;
			lblTime.setBounds(225, 10, cntShrink, 35);

			//If the width of the time bar is less than 40, make the bar red
			if (cntShrink < 40) 		

				lblTime.setBackground(Color.RED);

			//If the width of the time bar is less than 100, make the bar yellow
			else if (cntShrink < 100)

				lblTime.setBackground(Color.YELLOW);

			//If the time runs out, call the game over method
			if (count == 0) {

				gameOver();

			}

			//If all 5 caves are occupied, stop the timers
			if (Frog.caveCount == 5) {

				countDownTimer.stop();
				flyTimer.stop();

			}
		}

		//Checks if the fly timer is the source
		if (event.getSource() == flyTimer) {

			//For every odd number of time the fly timer ticks, randomly select 2 flies to appear, otherwise, make them invisible
			if (cntFlyTimer % 2 == 1) {				

				flyIndex1 = (int)(Math.random() * 4 );

				do 

					flyIndex2 = (int)(Math.random() * 4);

				while (flyIndex1 == flyIndex2);

				if (cave[flyIndex1].getIcon() == null)
					flies[flyIndex1].setVisible(true);

				if (cave[flyIndex2].getIcon() == null)
					flies[flyIndex2].setVisible(true);

			} else

				for (int x = 0; x < flies.length; x++)
					flies[x].setVisible(false);

			//Increment the number of fly timer ticks by 1
			cntFlyTimer++;

		}

	}

	//This method closes the this frame and opens the game over screen
	public static void gameOver() {

		GUI.dispose();
		new FroggerGameOver();

	}

	//Mandatory method for a key listener
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	//Mandatory method for a key listener
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}