import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;

public class FroggerGameOver extends JFrame implements ActionListener {

	//Instance fields
	private JLabel title = new JLabel ("Thanks for playing!"); //Label for the title
	private JButton exitGame = new JButton("Exit Game"); //Button to exit game 
	private JTextArea finalStats = new JTextArea (); //Text are to display the final score of the game
	private JLabel logo = new JLabel(); //Label to display the Frogger logo
	private Font titleFont = new Font ("Calibri", Font.BOLD, 46); //Font for the title
	private Font textFont = new Font ("Calibri", Font.PLAIN, 26); //Font for the text area
	private Font buttonFont = new Font ("Calibri", Font.BOLD, 32); //Font for the button
	private Clip clip; //Variable to enable sound (temporarily stores the sound file)

	//Constructor method
	public FroggerGameOver() {

		//Call methods
		frameSetup();
		printStats();
		buttonSetup();

		//Play the Frogger theme song
		try {

			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("sounds/froggerSong.wav")));
			clip.start();

		} catch (Exception e) {

			System.out.println("Error");

		}
	}

	//This method sets up the frame
	private void frameSetup() {

		//Frame setup
		setSize(600,600);
		setLayout(null);
		setTitle("Frogger Exit Screen");
		setVisible(true);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color (28, 36, 96));

		//Title setup
		title.setBounds(95, 10, 400, 80);
		title.setFont(titleFont);
		title.setForeground(Color.white);
		add(title);

		//logo setup
		logo.setBounds(100, 300, 360, 200);
		logo.setIcon(new ImageIcon (new ImageIcon ("images/FroggerLoading.jpg").getImage().getScaledInstance(360, 180, 0)));
		add(logo);
	}

	//This method sets up the button
	private void buttonSetup() {

		exitGame.setBounds(75, 505, 450, 50);
		exitGame.setFont(buttonFont);
		exitGame.setForeground(Color.white);
		exitGame.setOpaque(false);
		exitGame.setContentAreaFilled(false);
		exitGame.setBorderPainted(false);
		exitGame.setFocusPainted(false);
		exitGame.addActionListener(this);
		add(exitGame);

	}

	//This method sets up the text area
	private void printStats() {

		finalStats.setBounds(75, 45, 500, 260);
		finalStats.setFont(textFont);
		finalStats.setForeground(Color.white);
		finalStats.setBackground(new Color (28, 36, 96));
		finalStats.setLineWrap(true);
		finalStats.setWrapStyleWord(true);
		finalStats.setEditable(false);
		finalStats.setText("\n\n Your score:\t\t" + FroggerGUI.score + "\n\n Time remaining (times 10):\t" + FroggerGUI.count * 10 + "\n\n Your final score:\t"  + (FroggerGUI.score + (FroggerGUI.count * 10)));
		add(finalStats);

	}

	//This method implements the action listener
	public void actionPerformed(ActionEvent event) {

		//Checks if the source was the exit game button
		if (event.getSource() == exitGame) {

			//Exit the program
			System.exit(0);

		}


	}

}
