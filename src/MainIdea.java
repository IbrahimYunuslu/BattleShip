import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainIdea {
	
	private JFrame frame;
	private boolean isGameStillRunning;
	
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
	
	public void startGame(){
		isGameStillRunning = true;
		
		Boat[] p1Boats = initializeBoatCreation(true);
		Boat[] p2Boats = initializeBoatCreation(false);
		
		Grid grid = new Grid(chooseBoatPositions(p1Boats));
		MyGrid small = new MyGrid(chooseBoatPositions(p2Boats));
		small.setLocation(grid.getWidth()+10, grid.getY());
		
		int windowWidth = small.getX() + small.getWidth() + 10;
		frame.setPreferredSize(new Dimension(windowWidth, frame.getHeight()));
		frame.setSize(frame.getPreferredSize());
		frame.pack();
		frame.getContentPane().add(grid);
		frame.getContentPane().add(small);
		frame.addMouseListener(grid);
		frame.setVisible(true);

		gameLoop(p1Boats, p2Boats, grid, small);
	}
	
	private Boat[] initializeBoatCreation(boolean isPlayerOne) {
		Boat[] fourstep = createBoats(4, 1, isPlayerOne);
		Boat[] threestep = createBoats(3, 2, isPlayerOne);
		Boat[] twostep = createBoats(2, 3, isPlayerOne);
		Boat[] step = createBoats(1, 4, isPlayerOne);

		Boat[] ships = concatBoatArray(fourstep, threestep);
		ships = concatBoatArray(ships, twostep);
		ships = concatBoatArray(ships, step);

		return ships;
	}

	private Boat[] createBoats(int shipSize, int numOfBoats, boolean isPlayerOne) {
		Boat[] listOfBoats = new Boat[numOfBoats];
		for (int i = 0; i < numOfBoats; i++) {
			PlayerBoats[] shipArray = new PlayerBoats[shipSize];
			for (int j = 0; j < shipSize; j++) {
				PlayerBoats p = new PlayerBoats(isPlayerOne);
				shipArray[j] = p;
			}
			listOfBoats[i] = new Boat(shipArray);
		}

		return listOfBoats;
	}

	private Boat[] concatBoatArray(Boat[] a, Boat[] b) {
		int aLen = a.length;
		int bLen = b.length;
		Boat[] c = new Boat[aLen + bLen];
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);
		return c;
	}
	
	private Object[][] chooseBoatPositions(Boat[] ships){
		GridCreator creator = new GridCreator(ships, frame);
		creator.setup();
		frame.getContentPane().add(creator);
		frame.getContentPane().repaint();
		frame.setVisible(true);
		while (!creator.isSetupOver()) {}
		frame.getContentPane().removeAll();
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
		
		return creator.getGridArray();
	}
	
	private void betweenTurns(Grid grid, MyGrid small){
		frame.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ScreenSwitcher betweenTurns = new ScreenSwitcher((JPanel) frame.getContentPane(), grid, small);
				final Object[][] grid1Temp = grid.getArray();
				final Object[][] grid2Temp = small.getArray();
				if (!grid.isTurn() && isGameStillRunning){
					grid.setVisible(false);
					small.setVisible(false);
					grid.setArray(grid2Temp);
					small.setArray(grid1Temp); 
					betweenTurns.loadTurnScreen();
				}
			}
		});
	}
	
	private void gameLoop(Boat[] p1Boats, Boat[] p2Boats, Grid grid, MyGrid small){
		betweenTurns(grid, small);
		
		while (isGameStillRunning) {
			boolean p1AllBoatsDead = true;
			for (int i = 0; i < p1Boats.length; i++)
				if (p1Boats[i].checkIfDead())
					for (int j = 0; j < p1Boats[i].getPlayerBoats().length; j++)
						p1Boats[i].getPlayerBoats()[j].setBoatImage("dead.gif");
				else p1AllBoatsDead = false;

			boolean p2AllBoatsDead = true;
			grid.repaint();
			small.repaint();

			for (int i1 = 0; i1 < p2Boats.length; i1++)
				if (p2Boats[i1].checkIfDead())
					for (int j = 0; j < p2Boats[i1].getPlayerBoats().length; j++)
						p2Boats[i1].getPlayerBoats()[j].setBoatImage("dead.gif");
				else p2AllBoatsDead = false;
			

			grid.repaint();
			small.repaint();

			if (p1AllBoatsDead || p2AllBoatsDead) {
				isGameStillRunning = false;
				for (int i1 = 0; i1 < grid.getArray().length; i1++)
					for (int j = 0; j < grid.getArray()[i1].length; j++)
						if ((grid.getArray()[i1][j].equals((Object) 1)))
							grid.getArray()[i1][j] = (Object) 0;

				LooserWinner gameOver = new LooserWinner(frame, !p1AllBoatsDead);
				gameOver.loadEndScreen();
			}
		}
	}
}

class LaunchGame{
	public static void main(String[] args) {
		new MainIdea().setUpWindow();
	}
}
