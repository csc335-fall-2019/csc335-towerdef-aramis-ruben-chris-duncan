package game;
import java.io.FileNotFoundException;

import viewable.Viewable;
import viewable.gameObjects.Market;
import viewable.gameObjects.Tower;
import viewable.gameObjects.TowerType;

public class TowerDefenseController {
	private TowerDefenseBoard board;
	public TowerDefenseController(TowerDefenseView view) throws FileNotFoundException {
		board = new TowerDefenseBoard(view);
	}
	
	public TowerDefenseController(TowerDefenseView2 view) {
		board = new TowerDefenseBoard(view);
	}

	public Viewable[][][] getBoard() {
		// TODO Auto-generated method stub
		return board.getBoard();
	}
	
	public boolean canMove() {
		return false;
	}
	
	public void addTower(int row, int col, TowerType type) {
		board.addTower(row, col, type);
	}
	
	public Market getMarket() {
		return board.getMarket();
	}
}
