package prgrammer.coderj.framed;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;

public class LightButton extends JButton {

	private static final long serialVersionUID = 1L;
	private static final int MAXSIZE = 50;
	
	private int row = 0;
	private int col = 0;
	
	public LightButton(int row, int col) {
		this.row = row;
		this.col = col;
		setBackground(Color.BLACK);
		Dimension size = new Dimension(MAXSIZE, MAXSIZE);
		setPreferredSize(size);
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	// when on, light button is red
	public void turnOn() {
		setBackground(Color.RED);
	}
	
	// when off, black
	public void turnOff() {
		setBackground(Color.BLACK);
	}
	
	// check to see if button is on/off based on color
	public boolean isLit() {
		Color color = getBackground();
		boolean isLit = color.equals(color.RED);
		return isLit;
	}
	
	// light button switch
	public void toggle() {
		if(isLit()) {
			turnOff();
		}
		else {
			turnOn();
		}
	}
}
