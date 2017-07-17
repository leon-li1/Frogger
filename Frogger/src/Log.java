import java.awt.event.*;
import javax.swing.*;

public class Log extends JLabel implements ActionListener {

	//Instance fields
	private int startX; //start position (x)
	private int startY; //start position (y)
	private int speed; //Speed of the log (timer delay)
	private Timer moveTimer; //A move timer to allow the log to move

	//Constructor method
	public Log( int speed, int startX, int startY, String fileName, int width, int height){

		//Set the parameters given to its corresponding field
		this.startX = startX;
		this.startY = startY;
		this.speed = speed;
		
		//Set the size and Image Icon of the log
		setSize(width, height);
		setIcon(new ImageIcon("images/" + fileName + ".gif"));

		//Create the move timer and start it
		moveTimer = new Timer(speed, this);
		moveTimer.start();

	}
	
	//Setters and getters
	public int getStartX() {
			
		return startX;
	
	}

	public int getStartY() {
		
		return startY;
	
	}
	
	//This method implements the action listener
	public void actionPerformed(ActionEvent event) {
		
		//Checks if the source was the move timer
		if (event.getSource() == moveTimer){
			
			//Checks if the current log is completely off the screen 
			if (getX() > FroggerGUI.FRAME_WIDTH + getWidth())
				
				//Reset the log to its original position
				setLocation(-getWidth() * 2, startY);
			
			//if not then slide right 1 unit without changing the y value
			else {
				
				setLocation(getX() + 1, getY());
				
				//Checks if the frog has landed on the log
				if (getBounds().intersects(FroggerGUI.frog.getBounds())){
					
					//Sets frog's ride direction to the right 
					FroggerGUI.frog.setRideDirection(1);
					
					//Sets the frog's slide timer at the log's speed
					FroggerGUI.frog.getmoveTimer().setDelay(speed);
					
					//Start the frog's move/slide timer
					FroggerGUI.frog.getmoveTimer().start();
					
				}

			}
			
		}

	}

}

