
import java.awt.Point;

public class Boat {

	private PlayerBoats[] parts;
	private Point startingPosition;

	Boat(PlayerBoats[] list) {
		parts = list;
		startingPosition = new Point(0,0);
	}
	
	public boolean checkIfDead() {
		boolean isDead = true;
		for (int i = 0; i < parts.length; i++)	if (!parts[i].isDestroy()) isDead = false;
		return isDead;
	}

	public PlayerBoats[] getPlayerBoats() {
		return parts;
	}
	
	public void setStartingOffGridPosition(Point sp){
		startingPosition = sp;
	}
	
	public Point getStartingOffGridPosition(){
		return startingPosition;
	}
}
