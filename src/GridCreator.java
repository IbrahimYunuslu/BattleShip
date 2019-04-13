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
	
	public void setup() {
		int largestBoatSize = 0;
		for (int i = 0; i < shipArray.length; i++){
			int temp = shipArray[i].getPlayerBoats().length;
			if (temp > largestBoatSize)	largestBoatSize = temp;
		}
		
		int windowWidth = 54 + ((47 + 5) * gridArray.length) + (2 * 5) + 50 + ((largestBoatSize + 1) * 47);
		int windowHeight = 56 + ((47 + 5) * (gridArray.length + 1));
		if (windowHeight < 2 * 47 + (shipArray.length * (47 + 5 + 2)))	windowHeight = 2 * 47 + (shipArray.length * (47 + 5 + 2));
		window.setPreferredSize(new Dimension(windowWidth, windowHeight));
		window.setMinimumSize(new Dimension(windowWidth, windowHeight));
		window.pack();
		setSize(window.getContentPane().getSize());

		JLabel gridLabel = new JLabel(new ImageIcon(gridImage));
		gridLabel.setSize(54 + gridArray.length + 1 + ((47 + 5) * gridArray.length), 56 + gridArray.length + 1 + ((47 + 5) * (gridArray.length)));
		gridLabel.setLocation(0, 0);
		gridLabel.setHorizontalAlignment(SwingConstants.LEFT);
		gridLabel.setVerticalAlignment(SwingConstants.TOP);
		add(gridLabel);

		int buttonXPos = gridLabel.getWidth();

		randomizeBoatsBtn = new JButton("Random placing");
		randomizeBoatsBtn.setBounds(buttonXPos, 0, window.getWidth() - buttonXPos-150, 42);//buttonXPos, 0, window.getWidth() - buttonXPos, 47 - 25
		randomizeBoatsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Random rand = new Random();
				for (int i = 0; i < panelArray.length; i++)	panelArray[i].setLocation(shipArray[i].getStartingOffGridPosition());
				for (int i = 0; i < panelArray.length; i++) {
					int timeout = 0;
					while (timeout < 500 && shipArray[i].getStartingOffGridPosition().equals(panelArray[i].getLocation())) {
						int x = rand.nextInt(gridArray.length);
						int y = rand.nextInt(gridArray.length);
						timeout++;
						leftClick(i, x, y);
						if (rand.nextInt(2) == 0 && !shipArray[i].getStartingOffGridPosition().equals(panelArray[i].getLocation())) rightClick(i, x, y);
					}
				}
			}
		});
		add(randomizeBoatsBtn);
		randomizeBoatsBtn.setVisible(true);
		repaint();

		endSetup = new JButton("Done");
		endSetup.setBounds(buttonXPos, 42, window.getWidth() - buttonXPos-150, 42);//buttonXPos, 47 - 5, window.getWidth() - buttonXPos, window.getHeight()
		endSetup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setupOver = true;
			}
		});
		add(endSetup);
		endSetup.setVisible(false);
		
		//----------------------------------------
		JButton bot	= new JButton("Bot");
		bot.setBounds(buttonXPos+150, 0, window.getWidth() - buttonXPos-150, 42);
		add(bot);
		bot.setVisible(true);
		
		JButton multi = new JButton("Multiplayer");
		multi.setBounds(buttonXPos+150, 42, window.getWidth() - buttonXPos-150, 42);

		add(multi);
		multi.setVisible(true);
		//----------------------------------------

		for (int j = 0; j < shipArray.length; j++) {
			final int shipNum = j;
			
			JPanel panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setOpaque(false);
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			panel.add(Box.createRigidArea(new Dimension(0, 0)));

			for (int i = 0; i < shipArray[j].getPlayerBoats().length; i++) {
				ImageIcon icon = new ImageIcon(shipArray[j].getPlayerBoats()[i].getBoatImage());
				JLabel label = new JLabel(icon);
				panel.add(label);
				panel.add(Box.createRigidArea(new Dimension(5 + 2, 0)));
			}
			panel.setLocation(54 + ((47 + 5) * gridArray.length) + (2 * 5) + 50, 47 + 5 + 2 + (j * (47 + 5 + 2)));
			panel.setSize(shipArray[j].getPlayerBoats().length * (47 + 5), 47);
			shipArray[shipNum].setStartingOffGridPosition(panel.getLocation());
			add(panel);
			panelArray[j] = panel;
			setComponentZOrder(panel, 0);

			panel.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					if (SwingUtilities.isLeftMouseButton(e)) {
						JPanel component = (JPanel) e.getComponent().getParent().getParent();
						Point pt = new Point(SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), component));
						panel.setLocation((int) pt.getX() - (47 / 2), (int) pt.getY() - (47 / 2));
						currentlyPlacingBoat = true;
					}
				}

			});
			panel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					JPanel component = (JPanel) e.getComponent().getParent().getParent();
					Point pt = new Point(SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), component));
					int counter1 = 0;
					int counter2 = 0;
					int value = (int) pt.getX();
					while (54 + ((47 + 5) * counter1) < value)	counter1++;
					counter1--;
					int value2 = (int) (pt.getY());
					while (56 + ((47 + 5) * counter2) < value2)	counter2++;
					counter2--;
					if (e.getButton() == MouseEvent.BUTTON1) {
						currentlyPlacingBoat = false;
						leftClick(shipNum, counter1, counter2);
					} else if (e.getButton() == MouseEvent.BUTTON3)	rightClick(shipNum, counter1, counter2);
					endSetup.repaint();
				}

			});
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
