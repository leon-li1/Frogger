import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;

public class FroggerMainMenu extends JFrame implements ActionListener {

	//Instance fields
	private JLabel logo = new JLabel(new ImageIcon("images/FroggerLoading.jpg")); //Label to display the Frogger logo
	private JButton gameStart = new JButton("Start Game");  //Button to allow the game to start
	private JButton instructions = new JButton("Instructions"); //Button to show the game instructions
	private Font font = new Font ("Calibri", Font.BOLD, 36); //Font for the buttons
	private Clip clip; //Variable to enable sound (temporarily stores the sound file)
	
	//Constructor method
	public FroggerMainMenu() {

		//Play the introduction music
		try {

			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("sounds/music.wav")));
			clip.start();

		} catch (Exception e){

			System.out.println("Error");

		}
		
		//Call the setup methods
		frameSetup();
		buttonSetup();
		
	}

	//This method sets up the 2 buttons on the frame
	private void buttonSetup() {

		//Create the game start button
		gameStart.setBounds(100, 320, 400, 80);
		gameStart.setFont(font);
		gameStart.setForeground(Color.white);
		gameStart.setOpaque(false);
		gameStart.setContentAreaFilled(false);
		gameStart.setBorderPainted(false);
		gameStart.setFocusPainted(false);
		gameStart.addActionListener(this);
		add(gameStart);

		//Create the instructions button
		instructions.setBounds(100, 370, 400, 80);
		instructions.setFont(font);
		instructions.setForeground(Color.white);
		instructions.setOpaque(false);
		instructions.setContentAreaFilled(false);
		instructions.setBorderPainted(false);
		instructions.setFocusPainted(false);
		instructions.addActionListener(this);
		add(instructions);
	
	}

	//This method sets up the frame
	private void frameSetup() {
		
		//Frame setup
		setSize(600,600);
		setLayout(null);
		setTitle("Frogger Introduction Screen");
		setVisible(true);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color (28, 36, 96));
		
		//Add the logo
		logo.setBounds(45, 20, 500, 360);
		add(logo);
		
	}

	//This method implements the action listener
	public void actionPerformed(ActionEvent e) {

		//Checks if the source was the game start button
		if (e.getSource() == gameStart) {

			//Close the current frame and call up the Frogger GUI frame
			dispose();
			FroggerGUI gui = new FroggerGUI();
		}
		
		//Checks if the source was the instructions button
		else if (e.getSource() == instructions) {
			
			//Close the current frame and call up the Frogger Instructions frame
			dispose();
			new FroggerInstructions();
		}

	}

}