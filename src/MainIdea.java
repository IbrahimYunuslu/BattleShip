import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainIdea {

	public void setUpWindow() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(200, 300));
		frame.setMinimumSize(new Dimension(200, 300));
		frame.setResizable(false);
		frame.pack();
		startGame();
	}
}
class LaunchGame{
	public static void main(String[] args) {
		new MainIdea().setUpWindow();
	}
}
