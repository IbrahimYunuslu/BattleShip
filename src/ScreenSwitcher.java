import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScreenSwitcher implements MouseListener {

	private JPanel window;
	private JLabel bkgImageContainer;
	private volatile boolean isImageVisible;
	private Grid grid;
	private MyGrid small;
	
	public ScreenSwitcher(JPanel theWindow, Grid grid, MyGrid small){
		window = theWindow;
		bkgImageContainer = new JLabel(new ImageIcon(new ImageIcon("NextTurn.png").getImage().getScaledInstance(window.getWidth(), window.getHeight(), BufferedImage.SCALE_FAST)));
		bkgImageContainer.setSize(window.getWidth(), window.getHeight());
		bkgImageContainer.setLocation(0, 0); 
		isImageVisible = true;
		this.grid = grid;
		this.small = small;
	}
	
	public void loadTurnScreen() {
		window.add(bkgImageContainer);
		bkgImageContainer.addMouseListener(this);
		window.setVisible(true);
		window.repaint();
	}

	public boolean isImageVisible(){
		return isImageVisible;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		window.remove(bkgImageContainer);
		window.revalidate();
		window.repaint();
		grid.setTurn(true);
		grid.setVisible(true);
		small.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}
	
}
