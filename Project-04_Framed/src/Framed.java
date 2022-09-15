package prgrammer.coderj.framed;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import programmer.coderj.mycomponents.TitleLabel;

public class Framed extends JFrame {
	private static final long serialVersionUID = 1L;
	private final int GRIDSIZE = 3;
	private LightButton[][] lightButton = new LightButton[GRIDSIZE][GRIDSIZE];
	
	public Framed() {
		initGUI();
		
		setTitle("Framed");
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		newGame();
	}
	
	private void initGUI() {
		TitleLabel titleLabel = new TitleLabel("Framed");
		add(titleLabel, BorderLayout.PAGE_START);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(GRIDSIZE, GRIDSIZE));
		add(centerPanel, BorderLayout.CENTER);
		
		for(int row = 0; row < GRIDSIZE; row++) {
			for(int col = 0; col < GRIDSIZE; col++) {
				lightButton[row][col] = new LightButton(row, col);
				lightButton[row][col].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						LightButton button = (LightButton) e.getSource();
						int row = button.getRow();
						int col = button.getCol();
						toggleLights(row, col);
					}
				});
				centerPanel.add(lightButton[row][col]);
			}
		}
	}
	
	private void toggleLights(int row, int col) {
		lightButton[row][col].toggle();
		
		// top left corner
		if(row == 0 && col == 0) {
			lightButton[0][1].toggle();
			lightButton[1][0].toggle();
			lightButton[1][1].toggle();
		} 
		// top right
		else if(row == 0 && col == 2) {
			lightButton[0][1].toggle();
			lightButton[1][2].toggle();
			lightButton[1][1].toggle();
		} 
		// bottom left
		else if(row == 2 && col == 0) {
			lightButton[1][0].toggle();
			lightButton[2][1].toggle();
			lightButton[1][1].toggle();
		} 
		// bottom right
		else if(row == 2 && col == 2) {
			lightButton[1][2].toggle();
			lightButton[2][1].toggle();
			lightButton[1][1].toggle();
		} 
		// top row middle
		else if(row == 0 && col == 1) {
			lightButton[0][0].toggle();
			lightButton[0][2].toggle();
		}
		// bottom row middle
		else if(row == 2 && col == 1) {
			lightButton[2][0].toggle();
			lightButton[2][2].toggle();
		}
		// left side middle
		else if(row == 1 && col == 0) {
			lightButton[0][0].toggle();
			lightButton[2][0].toggle();
		}
		// right side middle
		else if(row == 1 && col == 2) {
			lightButton[0][2].toggle();
			lightButton[2][2].toggle();
		}
		// center
		else if(row == 1 && col == 1) {
			lightButton[0][1].toggle();
			lightButton[2][1].toggle();
			lightButton[1][0].toggle();
			lightButton[1][2].toggle();
		}
	}
	
	private void newGame() {
		// turn all lights on, then turn all lights off
		for(int row = 0; row < GRIDSIZE; row++) {
			for(int col = 0; col < GRIDSIZE; col++) {
				lightButton[row][col].turnOn();
			}
		}
		lightButton[1][1].turnOff();
		
		Random rand = new Random();
		int numberOfTimes = rand.nextInt(11) + 10;
		for(int i = 0; i < numberOfTimes; i++) {
			int row = rand.nextInt(GRIDSIZE);
			int col = rand.nextInt(GRIDSIZE);
			toggleLights(row, col);
			
			// check if the game is over 
			endGameIfDone();
		}
	}
	
	private void endGameIfDone() {
		// do the lights form on a frame?
		boolean done = lightButton[0][0].isLit()
				&& lightButton[0][1].isLit()
				&& lightButton[0][2].isLit()
				&& lightButton[1][0].isLit()
				&& !lightButton[1][1].isLit()
				&& lightButton[1][2].isLit()
				&& lightButton[2][0].isLit()
				&& lightButton[2][1].isLit()
				&& lightButton[2][2].isLit();
		if(done) {
			String message = "Congratulations! You won! Do you want to play again?";
			int option = JOptionPane.showConfirmDialog(this, message, "Play again?", JOptionPane.YES_NO_OPTION);
			if(option == JOptionPane.YES_OPTION) {
				newGame();
			}
			else {
				System.exit(0);
			}
		}
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
				new  Framed();
			}
		});
	}

}
