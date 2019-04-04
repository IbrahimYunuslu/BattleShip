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
}
