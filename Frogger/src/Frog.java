import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;

public class Frog extends JLabel implements ActionListener {

	//Class Fields
	public static boolean touchObstacle; //Is used to see whether or not frog touches a vehicle or a snake (to play the correct sound)
	public static int caveCount = 0; //Counts the number of caves that the frog has reached

	//Creates an array of images to show different directions of the frog as well as the state of it [direction][state]
	public static final ImageIcon[][] IMAGE = {
			{new ImageIcon("images/frog00.gif"), new ImageIcon("images/frogs01.gif"),new ImageIcon("images/frog02.gif") },
			{new ImageIcon("images/frog10.gif"), new ImageIcon("images/frogs11.gif"),new ImageIcon("images/frog12.gif") },
			{new ImageIcon("images/frog20.gif"), new ImageIcon("images/frogs21.gif"),new ImageIcon("images/frog22.gif") },
			{new ImageIcon("images/frog30.gif"), new ImageIcon("images/frogs31.gif"),new ImageIcon("images/frog32.gif") }
	};

	//Constants for frog's move amount and start position
	private static final int MOVE_AMOUNT = 50;
	private static final int START_X = 325;
	private static final int START_Y = 675;

	//Instance fields
	private int direction; //0 - left, 1 - up, 2 - right, 3 - down
	private int dx; //Frog's displacement in x direction
	private int dy; //Frog's displacement in y direction
	private int cntLostLife = 0; //Counts the number of times you lose a life
	private int jumpState = 0; //0 - sitting, 1 - flexing , 2 - flying, 3 - done (frog lands); refers to different jump states
	private Timer jumpTimer = new Timer (50, this); //A timer used to move the frog through its jump states
	private Timer moveTimer = new Timer (0, this); //Interval set to the same amount as the log or turtle the frog lands on; used to allow the frog to slide
	private int rideDirection = 0; //0 or 1 or -1 depending on whether the frog lands on a log or turtle
	private int lives = 5; //Amount of lives frog has left
	private Timer deathTimer = new Timer (250, this); //The timer that creates a death animation
	private int deathState; //Is the number of the current state in the death animation
	private Clip clip; //Variable to enable sound (temporarily stores the sound file)

	//Constructor method
	public Frog() {

		//Sets the starting location and starting ImageIcon for the frog (1 - facing upwards , 0 - sitting)
		setLocation(START_X,START_Y);
		setIcon(IMAGE[1][0]);
	}

	//Getters and setters
	public Timer getmoveTimer() {

		return moveTimer;

	}

	public int getRideDirection() {

		return rideDirection;

	}

	public void setRideDirection(int rideDirection) {

		this.rideDirection = rideDirection;

	}

	public int getLives() {

		return lives;

	}

	public void setLives(int lives) {

		this.lives = lives;

	}

	public Timer getDeathTimer() {
		return deathTimer;
	}

	//This method implements the action listener
	public void actionPerformed(ActionEvent event) {

		//Checks if the source was the jump timer
		if (event.getSource() == jumpTimer) { 

			//Increases the jump state of the frog by 1
			jumpState++; 

			//Checks if the frog's jump state is 3
			if (jumpState == 3) {

				jumpTimer.stop(); //Stops the jump timer
				jumpState = 0; //Sets the jump state to 0 (the original state of the frog)

			//Checks if the frog's jump state is 2
			} else if (jumpState == 2) 

				//Move the frog according to key pressed 
				setLocation(getX() + dx, getY() + dy);

			//Checks if the frog is in the home area
			if (getY() < 125) {  

				//Creates a loop to check each of the houses 
				for (int x = 0; x < FroggerGUI.flies.length ; x++) {

					//Checks if the current fly is visible and if the frog touches it
					if (FroggerGUI.flies[x].isVisible() == true && getBounds().intersects(FroggerGUI.flies[x].getBounds())) {

						//Increase the score by 200, update the score label and make the current fly invisible
						FroggerGUI.score += 200;
						FroggerGUI.lblScore.setText(FroggerGUI.scoreToString());
						FroggerGUI.flies[x].setVisible(false);

					}

					//Check for overlap between the frog and a cave and if there was no previous frog at the cave
					if (getBounds().intersects(FroggerGUI.cave[x].getBounds()) && FroggerGUI.cave[x].getIcon() == null) {

						//Play the "entering cave" sound
						try {

							clip = AudioSystem.getClip();
							clip.open(AudioSystem.getAudioInputStream(new File("sounds/home.wav")));
							clip.start();

						} catch (Exception e) {

							System.out.println("Error");

						}

						FroggerGUI.cave[x].setIcon(new ImageIcon("images/frogHome.gif")); //sets the ImageIcon of the home label to a picture of a frog
						caveCount++; //increase the number of houses the frog has reached

						FroggerGUI.score += 500; //Increase user's score by 500
						FroggerGUI.lblScore.setText(FroggerGUI.scoreToString()); //Set the text of the score label to the current score

						//Checks if all 5 caves are occupied
						if (caveCount == 5) { 

							//Stop the frog move timer and call the game over method
							moveTimer.stop();
							FroggerGUI.gameOver();

						//If victory condition is not met
						} else

							//Calls the reset method
							reset(); 

					}

				}

			//Checks if the frog is in the middle lane (the inactive zone) 
			} else if (getY() == 375) 

				//Stops the move timer (stop the frog from sliding)
				moveTimer.stop(); 

			//Sets the ImageIcon of the frog to its corresponding image
			setIcon(IMAGE[direction][jumpState]);

		}

		//Checks if the source was the move timer
		if (event.getSource() == moveTimer) 

			//Slides the frog in the direction of a log or turtle that it landed on
			setLocation(getX() + rideDirection, getY()); 

		//Checks if the source was the death timer
		if (event.getSource() == deathTimer) {

			//Increase the death state by 1
			deathState++;

			//Checks if it's the first tick of the death timer
			if (deathState == 1) {

				//Plays the corresponding death sound
				if (touchObstacle) {

					try {

						clip = AudioSystem.getClip();
						clip.open(AudioSystem.getAudioInputStream(new File("sounds/squash.wav")));
						clip.start();

					} catch (Exception e) {

						System.out.println("Error");

					}

				} else {

					try {

						clip = AudioSystem.getClip();
						clip.open(AudioSystem.getAudioInputStream(new File("sounds/plunk.wav")));
						clip.start();

					} catch (Exception e){

						System.out.println("Error");

					}
				}

				//Sets the ImageIcon of the frog to its corresponding death image
				setIcon(new ImageIcon("images/frogDead1.gif"));

				//If it's the second tick of the death timer, change the ImageIcon of the frog to its corresponding death image 
			} else if (deathState == 2)

				setIcon(new ImageIcon("images/frogDead2.gif"));

			//If it's the third tick of the death timer, change the ImageIcon of the frog to its corresponding death image
			else if (deathState == 3)

				setIcon(new ImageIcon("images/frogDead3.gif"));

			//If it's the fourth tick of the death timer, stop the death timer, reset the death state to 0 and call the lose life method
			else if (deathState == 4) {
				
				deathTimer.stop();
				deathState = 0;
				loseLife();
				
			}
		}

		//Checks if the frog went off the screen
		if (getX() < 0 || getX() > FroggerGUI.FRAME_WIDTH) { 

			//Play the death sound
			try {

				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File("sounds/squash.wav")));
				clip.start();

			} catch (Exception e) {

				System.out.println("Error");

			}

			//Call the lose life method
			loseLife();
		}

		//Checks if the frog is in the river area
		else if(getY() < 375) { 

			boolean isDead = true; //By default, we assume the frog is dead

			//Creates a loop to check each of the logs
			for (int x = 0 ; x < FroggerGUI.log.length ; x++) {

				//Checks for overlap between the frog and a log
				if (getBounds().intersects(FroggerGUI.log[x].getBounds()))

					isDead = false;

			}

			//Creates a loop to check each of the turtles
			for (int x = 0 ; x < FroggerGUI.turtle.length ; x++) {

				//Checks for overlap between the frog and a turtle
				if (getBounds().intersects(FroggerGUI.turtle[x].getBounds()))

					isDead = false;

			}

			//Creates a loop to check each of the homes
			for (int x = 0 ; x < FroggerGUI.cave.length ; x++) {

				//Checks for overlap between the frog and a home
				if (getBounds().intersects(FroggerGUI.cave[x].getBounds()))

					isDead = false;

			}

			//Checks if the frog is dead
			if (isDead) {

				//Set the touch obstacle boolean to false, stops the frog's move timer and start the death timer
				touchObstacle = false;
				moveTimer.stop();
				deathTimer.start();
			}

		}

	}

	//This method allows the frog to jump with respect to the arrow key pressed
	public void jump (int arrowDirection) {

		//Scale the arrow key based on ASCI code. 0 - left, 1 - up, 2 - right, 3 - down
		direction = arrowDirection - 37; 

		//Set dx and dy to 0
		dx = 0;
		dy = 0;

		//Checks if the death timer is not running
		if (deathTimer.isRunning() == false) {
			
			//Play the jumping sound
			try {

				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File("sounds/hop.wav")));
				clip.start();

			} catch (Exception e) {

				System.out.println("Error");

			}

			//Depending on which arrow key is pressed, set dx or dy to corresponding move amount
			if (arrowDirection == KeyEvent.VK_LEFT)		

				dx = -MOVE_AMOUNT;

			else if (arrowDirection == KeyEvent.VK_UP)

				dy = -MOVE_AMOUNT;

			else if (arrowDirection == KeyEvent.VK_RIGHT)

				dx = MOVE_AMOUNT;

			else if (arrowDirection == KeyEvent.VK_DOWN)

				dy = MOVE_AMOUNT;

			//Start the jump timer
			jumpTimer.start();

		}
	}

	//This method allows the frog to lose a life and also updates the frog lives images
	public void loseLife() {

		//Call the reset method
		reset();

		//Sets one of the frog live labels to the frog dead image
		FroggerGUI.lives[cntLostLife].setIcon(new ImageIcon (new ImageIcon ("images/frogDead.gif").getImage().getScaledInstance(30, 30, 0)));

		//Subtract 1 life from the frog's lives or life
		FroggerGUI.frog.setLives(FroggerGUI.frog.getLives() - 1);

		//Checks if the frog's lives is 0 
		if (FroggerGUI.frog.getLives() == 0) 

			//Call the game over method
			FroggerGUI.gameOver();

		//Increase the lose life counter by 1
		cntLostLife++;

	}

	//This method resets the position of the frog
	public void reset() {

		//Stop the move timer
		moveTimer.stop();

		//Stop the jump timer
		jumpTimer.stop();

		//Set jump state to 0
		jumpState = 0;

		//Sets the frog to its starting coordinates
		setLocation(START_X, START_Y);

		//Sets the frog's ImageIcon to its starting image
		setIcon(IMAGE[1][0]);

	}

}
