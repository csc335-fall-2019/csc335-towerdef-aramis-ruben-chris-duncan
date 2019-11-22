package game;
import viewable.Viewable;
import viewable.gameObjects.Tower;
import viewable.gameObjects.TowerType;

public class TowerDefenseController {
	TowerDefenseBoard board;
	public TowerDefenseController(TowerDefenseView view) {
		board = new TowerDefenseBoard(view);
	}

	public Viewable[][] getBoard() {
		// TODO Auto-generated method stub
		return board.getBoard();
	}
	
	public boolean canMove() {
		return false;
	}
	
	public void addTower(int row, int col, TowerType type) {
		board.addTower(row, col, type);
	}
}
