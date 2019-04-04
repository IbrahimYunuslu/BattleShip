import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Grid extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	private BufferedImage gridImage;
	private Object[][] array;

	private volatile boolean isTurn;
	private boolean state;

	public Grid(Object[][] arr) {
		String path = "gridLabels.png";
		array = arr;
		isTurn = true;
		state = false;
		setBackground(Color.white);
		setPreferredSize(new Dimension((54+ arr.length + 1 + ((47+5)*(arr).length)), 56+ arr.length + 1 + ((47+5)*(arr).length)));
		setSize(getPreferredSize());
		setLocation(0,0);

		try {
			gridImage = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("Failed to load image");
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(gridImage, 0, 0, this);

		for (int i = 0; i < array.length; i++)
			for (int j = 0; j < array[i].length; j++) 
				if (array[i][j].equals((Object) 1) || ((array[i][j]).getClass().getName().equals("PlayerBoats") && !((PlayerBoats) array[i][j]).isDestroy())) {
					g2.setColor(Color.orange);
					g2.fillRect(54 + i + 1 + ((47 + 5) * i), 56 + j + 1 + ((47 + 5) * j), 47+(5/2)-1, 47+(5/2)-1);
				} else if ((array[i][j]).getClass().getName().equals("PlayerBoats")) 
					g2.drawImage(((PlayerBoats) array[i][j]).getBoatImage(), 54 + i + ((47 + 5) * i) + 5/2, 56 + j + ((47 + 5) * j) + 5/2, this);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (isTurn && e.getButton() == MouseEvent.BUTTON1) {
			int counter1 = 0;
			while (54 + ((47 + 5) * counter1) + 5 < e.getX()) counter1++;

			counter1--;
			int value2 = e.getY() - (47 / 2);
			int counter2 = 0;
			while (56 + ((47 + 5) * counter2) + 5 < value2)
				counter2++;

			counter2--;
			if (counter1 < array.length && counter1 >= 0)
				if (counter2 < array[0].length && counter2 >= 0) {
					if (array[counter1][counter2].equals((Object) 1)) {
						array[counter1][counter2] = 0;
						repaint();
						isTurn = false;
					} else if ((array[counter1][counter2]).getClass().getName().equals("PlayerBoats") && !((PlayerBoats) array[counter1][counter2]).isDestroy()) {
						((PlayerBoats) array[counter1][counter2]).destroy();
						repaint();
						isTurn = false;
					}
					state = false;
				}
		}else if(!isTurn && e.getButton() == MouseEvent.BUTTON1) state = true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	public boolean isTurn() {
		return isTurn;
	}

	public void setTurn(boolean t) {
		isTurn = t;
	}

	public Object[][] getArray() {
		return array;
	}

	public void setArray(Object[][] arr) {
		array = arr;
	}
	
	public boolean getState(){
		return state;
	}
	
	public void setState(boolean s){
		state = s;
	}

}
