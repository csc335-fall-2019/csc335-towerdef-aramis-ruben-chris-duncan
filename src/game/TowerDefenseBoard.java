package game;
import java.util.Observable;

import viewable.Viewable;
import viewable.gameObjects.ArcherTower;
import viewable.gameObjects.Map;
import viewable.gameObjects.Tower;
import viewable.gameObjects.TowerType;

public class TowerDefenseBoard extends Observable{	
	private Map board;
	public TowerDefenseBoard(TowerDefenseView view) {
		board = new Map();
		addObserver(view);
	}
	
	public void addTower(int row, int col, TowerType type){
		Viewable[][] boardArr = board.getBoard();
		switch(type) {
			case BASICTOWER:
				boardArr[col][row] = new ArcherTower();
				break;
			default:
				break;
		}
		setChanged();
		notifyObservers(row+" "+col);
	}
	
	public Viewable[][] getBoard() {
		return board.getBoard();
	}
}
