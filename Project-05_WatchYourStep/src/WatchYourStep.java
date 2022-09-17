package programmer.coderj.watchyourstep;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import programmer.coderj.mycomponents.TitleLabel;

public class WatchYourStep extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int GRIDSIZE = 10;
	private static final int NUMBEROFHOLES = 10;
	
	private TerrainButton[][] terrain = new TerrainButton[GRIDSIZE][GRIDSIZE];
	private int totalRevealed = 0;

	public WatchYourStep() {
		initGUI();
		setHoles();
		
		setTitle("Watch Your Step");
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void initGUI() {
		TitleLabel titleLabel = new TitleLabel("Watch Your Step");
		add(titleLabel, BorderLayout.PAGE_START);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(GRIDSIZE, GRIDSIZE));
		add(centerPanel, BorderLayout.CENTER);
		
		for(int row = 0; row < GRIDSIZE; row++) {
			for(int col = 0; col < GRIDSIZE; col++) {
				terrain[row][col] = new TerrainButton(row, col);
				// listen for click
				terrain[row][col].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						TerrainButton button = (TerrainButton) e.getSource();
						int row = button.getRow();
						int col = button.getCol();
						clickedTerrain(row, col);
					}
				});
				centerPanel.add(terrain[row][col]);
			}
		}
	}
	
	// display ten black squares
	private void setHoles() {
		Random rand = new Random();
		for(int i = 0; i < NUMBEROFHOLES; i++) {
			int pickRow = rand.nextInt(GRIDSIZE);
			int pickCol = rand.nextInt(GRIDSIZE);
			while(terrain[pickRow][pickCol].hasHole()) {
				pickRow = rand.nextInt(GRIDSIZE);
				pickCol = rand.nextInt(GRIDSIZE);
			}
			terrain[pickRow][pickCol].setHole(true);
			addToNeighborsHoleCount(pickRow, pickCol); // count number of neighboring tiles with hole
		}
	}
	
	private void addToNeighborsHoleCount(int row, int col) {
		addToHoleCount(row-1, col-1);
		addToHoleCount(row-1, col);
		addToHoleCount(row-1, col+1);
		addToHoleCount(row, col-1);
		addToHoleCount(row, col+1);
		addToHoleCount(row+1, col-1);
		addToHoleCount(row+1, col);
		addToHoleCount(row+1, col+1);
	}
	
	// surround each black button with cyan buttons
	// each cyan button displays number of black holes next to it
	private void addToHoleCount(int row, int col) {
		// if row and col refer to a valid neighbor
		if(row > -1 && row < GRIDSIZE && col > -1 && col < GRIDSIZE 
				&& !terrain[row][col].hasHole() && !terrain[row][col].isRevealed()) {
			terrain[row][col].increaseHoleCount(); // increase hole count at terrain element
		}
	}
	
	// check row and col of clicked terrain
	private void clickedTerrain(int row, int col) {
		// if the user steps on a hole, end the game
		if(terrain[row][col].hasHole()) {
			String message = "You have stepped on a hole. The game is over. Would you like to play again?";
			promptForNewGame(message);
		}
		else {
			check(row, col);
			checkNeighbors(row, col);
			if(totalRevealed == GRIDSIZE * GRIDSIZE - NUMBEROFHOLES) {
				String message = "Congratulations! You won the game!";
				promptForNewGame(message);
			}
		}
	}
	
	// display clicked numberless terrain as a cyan square without a number
	private void check(int row, int col) {
		if(row > -1 && row < GRIDSIZE && col > -1 && col < GRIDSIZE
				&& !terrain[row][col].hasHole() && !terrain[row][col].isRevealed()) {
			terrain[row][col].reveal(true);
			totalRevealed++; // increment every time terrain is revealed
			if(!terrain[row][col].isNextToHoles()) {
				checkNeighbors(row, col);
			}
		}
	}
	
	// when terrain button that is not revealed is clicked,
	// reveal all neighbors that lack holes
	private void checkNeighbors(int row, int col) {
		check(row-1, col-1);
		check(row-1, col);
		check(row-1, col+1);
		check(row, col-1);
		check(row, col+1);
		check(row+1, col-1);
		check(row+1, col);
		check(row+1, col+1);
	} 
	
	private void promptForNewGame(String message) {
		showHoles();
		int option = JOptionPane.showConfirmDialog(this, message,
		"Play Again?", JOptionPane.YES_NO_OPTION);
		// if user chooses to play again, create new game
		if(option == JOptionPane.YES_OPTION) {
			newGame();
		} 
		// exit the game
		else {
			System.exit(0);
		}
	}
	
	private void newGame() {
		for(int row = 0; row < GRIDSIZE; row++) {
			for(int col = 0; col < GRIDSIZE; col++) {
				terrain[row][col].reset();
			}
		}
		setHoles();
		totalRevealed = 0;
	}
	
	// reveal holes at the end of every game
	private void showHoles() {
		for(int row = 0; row < GRIDSIZE; row++) {
			for(int col = 0; col < GRIDSIZE; col++) {
				if(terrain[row][col].hasHole()) {
					terrain[row][col].reveal(true);
				}
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
				new  WatchYourStep();
			}
		});
	}

}
