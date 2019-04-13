import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class GridCreator extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage gridImage = null;
	private Object[][] gridArray;
	private Boat[] shipArray;
	private JPanel[] panelArray;
	private JButton endSetup, randomizeBoatsBtn;
	private JFrame window;
	private volatile boolean setupOver = false;
	public static boolean currentlyPlacingBoat = false;

	public GridCreator(Boat[] shipArray, JFrame app) {
		setLayout(null);
		setBackground(Color.white);
		setLocation(0,0);
		window = app;

		Object[][] grid = new Object[10][10];
		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[i].length; j++)
				grid[i][j] = 1;
		gridArray = grid;
		this.shipArray = shipArray;
		panelArray = new JPanel[shipArray.length];
		try {
			gridImage = ImageIO.read(new File("gridLabels.png"));
		} catch (IOException e) {
			System.out.println("Failed to load image");
		}
	}
		
	private void rightClick(int shipNum, int x, int y) {
		boolean isVertical = false;
		if (((BoxLayout) panelArray[shipNum].getLayout()).getAxis() == BoxLayout.Y_AXIS)	isVertical = true;
		removeBoatFromGridArray(shipArray[shipNum], isVertical);
		if (rotatePanel(panelArray[shipNum]) && !currentlyPlacingBoat)	addBoatToGridArray(shipArray[shipNum], new Point(x, y), !isVertical);
		else if (!currentlyPlacingBoat) {
			panelArray[shipNum].setLocation(shipArray[shipNum].getStartingOffGridPosition());
			rotatePanel(panelArray[shipNum]);
		}
		showDoneButton();
	}
	
	private void leftClick(int shipNum, int x, int y) {
		if ((((BoxLayout) panelArray[shipNum].getLayout()).getAxis() == BoxLayout.X_AXIS)) {
			if (x < gridArray.length - panelArray[shipNum].getWidth() / 47 + 1 && x >= 0)
				if (y < gridArray[0].length - panelArray[shipNum].getHeight() / 47 + 1 && y >= 0)
					placeBoatPanelOnGrid(x, y, shipNum, false);
				else {
					panelArray[shipNum].setLocation(shipArray[shipNum].getStartingOffGridPosition());
					removeBoatFromGridArray(shipArray[shipNum], false);
				}
			else {
				panelArray[shipNum].setLocation(shipArray[shipNum].getStartingOffGridPosition());
				removeBoatFromGridArray(shipArray[shipNum], false);
			}
		} else
			if (x < gridArray.length - panelArray[shipNum].getWidth() / 47 + 1 && x >= 0) {
				if (y < gridArray[0].length - panelArray[shipNum].getHeight() / 47 + 1 && y >= 0)
					placeBoatPanelOnGrid(x, y, shipNum, true);
				else {
					rotatePanel(panelArray[shipNum]);
					panelArray[shipNum].setLocation(shipArray[shipNum].getStartingOffGridPosition());
					removeBoatFromGridArray(shipArray[shipNum], true);
				}
			} else {
				rotatePanel(panelArray[shipNum]);
				panelArray[shipNum].setLocation(shipArray[shipNum].getStartingOffGridPosition());
				removeBoatFromGridArray(shipArray[shipNum], true);
			}
		showDoneButton();
	}
	
	public Object[][] getGridArray() {
		return gridArray;
	}

	public boolean isSetupOver() {
		return setupOver;
	}

	public JButton getButton() {
		return endSetup;
	}

}
