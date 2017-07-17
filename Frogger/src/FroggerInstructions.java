import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FroggerInstructions extends JFrame implements ActionListener{

	//Instance fields
	private JLabel lblTitle = new JLabel ("Instructions"); //Label for the title
	private JButton gameStart = new JButton("Start Game"); //Button to allow the game to start
	private JTextArea instructions = new JTextArea (); //Text area to display the instructions
	private Font titleFont = new Font ("Calibri", Font.BOLD, 46); //Font for the title
	private Font textFont = new Font ("Calibri", Font.PLAIN, 26); //Font for the instructions
	private Font buttonFont = new Font ("Calibri", Font.BOLD, 32); //Font for the buttons

	public FroggerInstructions() {

		//Call methods
		frameSetup();
		printInstructions();
		buttonSetup();

	}

	//This method sets up the frame
	private void frameSetup() {

		
		//Frame setup
		setSize(600,600);
		setLayout(null);
		setTitle("Frogger Intructions Screen");
		setVisible(true);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color (28, 36, 96));

		//Title setup
		lblTitle.setBounds(185, 30, 400, 80);
		lblTitle.setFont(titleFont);
		lblTitle.setForeground(Color.white);
		add(lblTitle);

	}

	//This method sets up the button
	private void buttonSetup() {

		gameStart.setBounds(95, 450, 400, 80);
		gameStart.setFont(buttonFont);
		gameStart.setForeground(Color.white);
		gameStart.setOpaque(false);
		gameStart.setContentAreaFilled(false);
		gameStart.setBorderPainted(false);
		gameStart.setFocusPainted(false);
		gameStart.addActionListener(this);
		add(gameStart);

	}

	//This method sets up the instructions label
	private void printInstructions() {

		instructions.setBounds(50, 95, 500, 360);
		instructions.setFont(textFont);
		instructions.setForeground(Color.white);
		instructions.setBackground(new Color (28, 36, 96));
		instructions.setLineWrap(true);
		instructions.setWrapStyleWord(true);
		instructions.setEditable(false);
		instructions.setText("1. Use the Arrow Keys to move the frog.\n"
			+ "2. Try to get the frog to occupy a cave while avoiding obstacles (i.e. Water, cars).\n"
			+ "3. Your score increases by 10 as you start moving and catching a fly will give you bonus score of 200 points.\n" 
			+ "4. Once all 5 caves are occupied or the frog runs out of lives, the game ends.\n" 
			+ "5. You have 120 seconds to complete the game and the time remaining (multiplied by 10) will be added to your score.\n");
		add(instructions);

	}

	//This method implements the action listener
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == gameStart) {

			//Close the current frame and open the GUI frame
			dispose();
			FroggerGUI gui = new FroggerGUI();

		}

	}

}
