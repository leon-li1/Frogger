
import java.awt.event.*;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class Turtle extends JLabel implements ActionListener {

	//store the number of turtles and its start position
	private int numTurtles;
	private int startX;
	private int startY;

	//set the turtles speed and create variables to handle diving 
	private static final int SPEED = 10;
	private Timer moveTimer = new Timer (SPEED,this);
	private Timer diveTimer = new Timer (500,this);
	private int state = 0;
	private int maxDive;
	private boolean diving = true;
	private Clip clip;

	//constructor
	public Turtle (int numTurtles,int maxDive, int startX, int startY, String fileName,int width, int height){

		this.startX = startX;
		this.startY = startY;
		setSize(width,height);

		setIcon(new ImageIcon("images/"+fileName+".gif"));

		this.numTurtles = numTurtles;
		this.maxDive = maxDive;

		diveTimer.start();
		moveTimer.start();

	}

	public int getStartX() {

		return startX;
	}

	public int getStartY() {

		return startY;
	}

	public void setMaxDive(int maxDive) {

		this.maxDive = maxDive;
	}

	public void actionPerformed(ActionEvent event) {

		//getting the movetimer
		if (event.getSource() == moveTimer){

			int x = getX();

			//checking if turtles are completely off screen
			if (x < -getWidth())

				//resetting at original location
				setLocation(FroggerGUI.FRAME_WIDTH,startY);

			//if not then slide left 1 value without getting changing y value
			else {

				setLocation(x - 1, getY());

				// Checks for overlap between a turtle and the frog
				if (getBounds().intersects(FroggerGUI.frog.getBounds())){

					//Makes the frog slide in the same direction and speed as a turtle 
					FroggerGUI.frog.setRideDirection(-1);
					FroggerGUI.frog.getmoveTimer().setDelay(SPEED);
					FroggerGUI.frog.getmoveTimer().start();
				}

			}

		} else if (event.getSource() == diveTimer) {

			setIcon (new ImageIcon ("images/turtles" + numTurtles + state + ".gif"));

			if (diving)

				state++;

			else

				state--;

			if (state == maxDive) {

				diving = false;

				if (state == 3 && getBounds().intersects(FroggerGUI.frog.getBounds()) || state == 4 && getBounds().intersects(FroggerGUI.frog.getBounds())) {

					Frog.touchObstacle = false;
					FroggerGUI.frog.getDeathTimer().start();
				
				}

			} else if (state == 0)

				diving = true;

		}

	}

}