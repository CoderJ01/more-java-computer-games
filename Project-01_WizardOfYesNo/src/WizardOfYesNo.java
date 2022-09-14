package programmer.coderj.wizardofyesorno;

import java.awt.Event;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class WizardOfYesNo extends JFrame {
	// prevent serial version UID warning
	private static final long serialVersionUID = 1L;
	
	private static final String[] ANSWER = 
		{"Yes.", 
		"Go for it!",
		"No.", 
		"I wouldn't."};
	
	public WizardOfYesNo() {
		Random rand = new Random();
		int numberOfAnswers = ANSWER.length;
		int pick = rand.nextInt(numberOfAnswers);
		String answer = ANSWER[pick];
		
		JLabel label = new JLabel(); // create window frame
		label.setText(answer);
		Font font = new Font(Font.SERIF, Font.BOLD, 32);
		label.setFont(font);
		label.setHorizontalAlignment(JLabel.CENTER);
		add(label);
		
		setTitle("Wizard of Yes/No.");
		setResizable(false);
		pack(); // set window to smallest size that will hold answers
		setLocationRelativeTo(null); // center the screen
		setVisible(true); // make frame visible
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		try {
			// modify window appearence
			String className = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(className);
		}
		catch (Exception e){
			
		}
		// EventQueue makes program run faster
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new WizardOfYesNo();
			}
		});
	}

}
