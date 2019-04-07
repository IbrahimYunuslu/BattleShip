import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MyGrid extends JPanel {
	private static final long serialVersionUID = 1L;
	private Object[][] array;
	private BufferedImage gridImage;

	public MyGrid(Object[][] array) {
		this.array = array;
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension((23 + 2 + (20+3)*array.length), 39+ 2 + ((20+3)*array.length)));
		setSize(getPreferredSize());
		try {
			gridImage = ImageIO.read(new File("gridSmallLabels.png"));
		} catch (IOException e) {
			System.out.println("Failed to load image");
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(gridImage, 0, 15, this);
		for (int i = 0; i < array.length; i++)
			for (int j = 0; j < array[i].length; j++)
				if ((array[i][j]).getClass().getName().equals("PlayerBoats"))	g2.drawImage(((PlayerBoats) array[i][j]).getBoatImage(), 23 + 2 + ((20 + 3) * i) + 3/2, 39 + 2 + ((20 + 3) * j) + 3/2, 18, 18, this);
	}

	

}
