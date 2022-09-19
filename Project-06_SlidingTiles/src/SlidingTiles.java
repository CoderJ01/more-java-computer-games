package programmer.coderj.slidingtiles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import programmer.coderj.mycomponents.TitleLabel;

public class SlidingTiles extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String FILENAME = "slidingTilesImage.jpg";
	private int tileSize = 50;
	private int gridSize = 4;
	BufferedImage image = null;
	private TileButton[][] tile = new TileButton[gridSize][gridSize];
	private JPanel centerPanel = new JPanel();
	private static final int UP = 0;
	private static final int DOWN = 1;
	private static final int LEFT = 2;
	private static final int RIGHT = 3;
	
	public SlidingTiles(){
		try {
			image = ImageIO.read(new File(FILENAME));
			TileButton.setTileSizeAndMaxTiles(tileSize, gridSize * gridSize);
			initGUI();
			
			setTitle("Sliding Tiles");
			setResizable(false);
			pack();
			setLocationRelativeTo(null);
			setVisible(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
		catch (IOException e) {
			// display error message if the file can't be found
			String message = FILENAME + " could not be found."; 
			JOptionPane.showMessageDialog(this, message);
		}
		
	}
	
	private void initGUI() {
		TitleLabel titleLabel = new TitleLabel("Sliding Tiles");
		add(titleLabel, BorderLayout.PAGE_START);
		
		// main panel
		divideImage();
		
		// button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.BLACK);
		add(buttonPanel, BorderLayout.PAGE_START);
		
		// scramble button
		JButton scrambleButton = new JButton("Scramble");
		scrambleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});
		buttonPanel.add(scrambleButton);
	}
	
	// divide image into 16 sub-images
	private void divideImage() {
		centerPanel.setLayout(new GridLayout(gridSize, gridSize));
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.removeAll(); // remove all center components from centerPanel
		
		int imageId = 0;
		for(int row = 0; row < gridSize; row++) {
			for(int col = 0; col < gridSize; col++) {
				int x = col * tileSize;
				int y = row * tileSize;
				BufferedImage subimage = image.getSubimage(x, y, tileSize, tileSize);
				ImageIcon imageIcon = new ImageIcon(subimage);
				tile[row][col] = new TileButton(imageIcon, imageId, row, col);
				// process tile clicks
				tile[row][col].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						TileButton button = (TileButton) e.getSource();
						clickedTile(button);
					}
				});
				centerPanel.add(tile[row][col]);
				imageId++;
			}
		}
		// re-validate centerPanel
		centerPanel.revalidate();
		scramble();
	}
	
	// swap images of clicked button and empty (white space) button
	private void clickedTile(TileButton clickedTile) {
		int row = clickedTile.getRow();
		int col = clickedTile.getCol();
		
		if(row > 0 && tile[row - 1][col].hasNoImage()) {
			clickedTile.swap(tile[row - 1][col]);
		}
		else if(row < (gridSize - 1) && tile[row + 1][col].hasNoImage()) {
			clickedTile.swap(tile[row + 1][col]);
		}
		else if(col > 0 && tile[row][col - 1].hasNoImage()) {
			clickedTile.swap(tile[row][col - 1]);
		}
		else if(col < (gridSize - 1) && tile[row][col + 1].hasNoImage()) {
			clickedTile.swap(tile[row][col + 1]);
		}
		
		// show image for title in the bottom right corner
		if(imagesInOrder()) {
			tile[gridSize - 1][gridSize - 1].showImage();
		}
	}
	
	// start window off with scrambled images
	private void scramble() {
		// bottom right tile is blank
		int openRow = gridSize - 1;
		int openCol = gridSize - 1;
		
		Random rand = new Random();
		
		for(int i = 0; i < (25 * gridSize); i++) {
			int direction = rand.nextInt(gridSize);
			switch(direction) {
			case UP:
				if(openRow > 0) {
					tile[openRow][openCol].swap(tile[openRow - 1][openCol]);
					openRow--;
				}
				break;
			case DOWN:
				if(openRow < (gridSize - 1)) {
					tile[openRow][openCol].swap(tile[openRow + 1][openCol]);
					openRow++;
				}
				break;
			case LEFT:
				if(openCol > 0) {
					tile[openRow][openCol].swap(tile[openRow][openCol - 1]);
					openCol--;
				}
				break;
			case RIGHT:
				if(openCol < (gridSize - 1)) {
					tile[openRow][openCol].swap(tile[openRow][openCol + 1]);
					openCol++;
				}
				break;
			}
		}
	}
	
	// check whether all image IDs are in order
	private boolean imagesInOrder() {
		int id = 0;
		boolean inOrder = true;
		for(int row = 0; row < gridSize && inOrder == true; row++) {
			for(int col = 0; col < gridSize && inOrder == true; col++) {
				int currentId = tile[row][col].getImageId();
				if(currentId != id) {
					inOrder = false;
				}
			}
		}
		return inOrder;
	}
	
	// start a new game
	private void newGame() {
		int imageId = 0;
		for(int row = 0; row < gridSize; row++) {
			for(int col = 0; col < gridSize; col++) {
				int x = col * tileSize;
				int y = row * tileSize;
				BufferedImage subimage = image.getSubimage(x, y, tileSize, tileSize);
				ImageIcon imageIcon = new ImageIcon(subimage);
				tile[row][col].setImage(imageIcon, imageId);
				imageId++;
			}
		}
		scramble();
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
				new  SlidingTiles();
			}
		});
	}

}
