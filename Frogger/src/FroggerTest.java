/*
 * Name: Leon Li
 * Date: May 17, 2017
 * Course code: ICS3U1-01
 * Extra features: Loading screen, menu screen, instructions, game timer, game timer bar, score, lives, snake in the middle, sounds,
 * flies in homes, death animation and game over screen.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FroggerTest extends JWindow implements ActionListener {
	
	//Instance fields
	private JPanel panel = new JPanel(); //creates a new panel
	private JProgressBar loadingBar = new JProgressBar(); //creates the progress bar
	private Timer progressBarTimer = new Timer(10, this); //timer
	private int count = 0; //counts the loading time
	private int max = 150; //time reached in order to exit progress bar
	
	//Constructor method
	public FroggerTest() {

		//Calls the set up method
		objectsSetup();
		
		//Start the progress bar timer
		progressBarTimer.start();
	}

	//This method sets up the objects in the class
	private void objectsSetup() {
	
		//Panel setup
		panel.setLayout(new BorderLayout()); 
		JLabel splashImage = new JLabel(new ImageIcon("images/FroggerLoading.jpg")); 
		panel.add(splashImage); 
		
		//Progress bar set up
		loadingBar.setMaximum(max);
		loadingBar.setForeground(new Color(50, 150, 200));
		loadingBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.add(loadingBar, BorderLayout.SOUTH);
		
		//Frame setup
		setContentPane(panel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}		

	//Main method
	public static void main(String[] args) {

		//Runs the constructor method
		new FroggerTest();
	}

	//This method implements the action listener
	public void actionPerformed(ActionEvent event) {

		//Checks if the source was the game start button
		if (event.getSource() == progressBarTimer) {
			
			//Set the value of the progress bar to count
			loadingBar.setValue(count);

			//Checks if the count is equal to the max
			if (count == max) {
				
				//Close the current window, stop the timers and open the main menu
				FroggerTest.this.dispose();
				progressBarTimer.stop();
				new FroggerMainMenu();
			
			}

			//Increase count by 1
			count++;
		
		}
		
	}

}
