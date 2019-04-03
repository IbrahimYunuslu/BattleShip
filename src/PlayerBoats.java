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

}
