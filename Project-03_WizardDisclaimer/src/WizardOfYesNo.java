package programmer.coderj.wizarddisclaimer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import programmer.coderj.mycomponents.TitleLabel;

public class WizardOfYesNo extends JFrame {
	// prevent serial version UID warning
	private static final long serialVersionUID = 1L;
	
	private static final String[] ANSWER = 
		{"Yes.", 
		"Go for it!",
		"No.", 
		"I wouldn't."};
	
	public WizardOfYesNo() {
		TitleLabel titleLabel = new TitleLabel("Wizard of Yes/No");
		add(titleLabel, BorderLayout.PAGE_START);
		
		Random rand = new Random();
		int numberOfAnswers = ANSWER.length;
		int pick = rand.nextInt(numberOfAnswers);
		String answer = ANSWER[pick];
		
		JLabel label = new JLabel(); // create window frame
		label.setText(answer);
		Font font = new Font(Font.SERIF, Font.BOLD, 32);
		label.setFont(font);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setOpaque(true);
		if(pick > 1) {
			label.setBackground(Color.red);
		}
		else {
			label.setBackground(Color.green);
		}
		add(label, JLabel.CENTER);
		
		String disclaimer = "This is only a suggestion. Use your own good judgement. The Wizard of"
				+ " Yes/No is not responsible for the consequence of your decisions.";
		JTextArea disclaimerTextArea = new JTextArea(disclaimer);
		disclaimerTextArea.setLineWrap(true);
		disclaimerTextArea.setWrapStyleWord(true);
		disclaimerTextArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(disclaimerTextArea);
		Dimension size = new Dimension(0, 50);
		scrollPane.setPreferredSize(size);
		add(scrollPane, BorderLayout.PAGE_END);
		
		setTitle("Wizard of Yes/No.");
		setResizable(false);
		pack(); // set window to smallest size that will hold answers
		setLocationRelativeTo(null); // center the screen
		setVisible(true); // make frame visible
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		try {
			// modify window appearance
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

