import java.awt.*;
import javax.swing.ImageIcon;

public class PlayerBoats {
	private Image shipPieceAlive;
	private boolean shipIsDead;
	boolean isPlayer1;

	public PlayerBoats(boolean isPlayer1) {
		this.isPlayer1 = isPlayer1;
		if (isPlayer1)	shipPieceAlive = new ImageIcon("player1anim.gif").getImage();
		else	shipPieceAlive = new ImageIcon("player2anim.gif").getImage();
		shipIsDead = false;
	}

	public void setBoatImage(String file) {
		shipPieceAlive = new ImageIcon(file).getImage();
	}

	public Image getBoatImage() {
		return shipPieceAlive;
	}

	public void destroy() {
		shipIsDead = true;
		if (isPlayer1)	setBoatImage("hit1anim.gif");
		else setBoatImage("hit2anim.gif");
	}

	public boolean isDestroy() {
		return shipIsDead;
	}

}
