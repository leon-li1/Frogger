import java.awt.event.*;
import javax.swing.*;

public class Snake extends JLabel implements ActionListener {

	//Instance fields
	//set the snakes speed and create variables to handle diving 
	private Timer moveTimer = new Timer (10, this); //Timer to allow the snake to move across the frame
	private Timer slitherTimer = new Timer (500, this); //Timer to allow the snake to appear as slithering (to change states)
	private int state = 0; //The snake's slither state
	private boolean slither = true; //A condition to control the snake's slithering
	
	//Constructor method
	public Snake () {

		setSize(100, 35);
		setIcon(new ImageIcon("images/snake0.gif"));

		slitherTimer.start();
		moveTimer.start();

	}

	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == moveTimer) {

			int x = getX();

			if (x < -100)

				setLocation(FroggerGUI.FRAME_WIDTH, 385);

			else {
				
				setLocation(x - 1, getY());

				if (getBounds().intersects(FroggerGUI.frog.getBounds())) {
					
					Frog.touchObstacle = true;
					FroggerGUI.frog.getDeathTimer().start();
				}
				
			}

		} else if (event.getSource() == slitherTimer) {

			setIcon (new ImageIcon ("images/snake" + state + ".gif"));

			if (slither)

				state++;

			else

				state--;

			if (state == 2) {

				slither = false;

			} else if (state == 0)

				slither = true;
		}

	}

}