import java.awt.event.*;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class Vehicle extends JLabel implements ActionListener {

	//vehicle's Direction, start position and width
	private int direction;
	private int startX;
	private int startY;
	private int width;

	//timer for moving
	private Timer moveTimer;

	//construct method
	public Vehicle (int direction, int speed, int startX, int startY, String fileName, int width, int height) {

		this.direction = direction;
		this.startX = startX;
		this.startY = startY;
		this.width = width;
		setSize(width,height);

		setIcon(new ImageIcon("images/" + fileName + ".gif"));

		moveTimer = new Timer(speed,this);
		moveTimer.start();

	}
	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public void actionPerformed(ActionEvent event) {
		
		if (event.getSource() == moveTimer) {
			int x = getX();

			if (direction == 1 && x < -width)
				setLocation(FroggerGUI.FRAME_WIDTH, startY);

			else if (direction == -1 && x > FroggerGUI.FRAME_WIDTH)
				setLocation(-width,startY);

			else{
				setLocation(x - direction, getY());
				
				if (getBounds().intersects(FroggerGUI.frog.getBounds())) {
					
					Frog.touchObstacle = true;
					FroggerGUI.frog.getDeathTimer().start();
				}
			}
			
		}
		
	}
	
}



