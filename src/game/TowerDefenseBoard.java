package game;
import java.util.Observable;

import viewable.Viewable;
import viewable.towers.ArcherTower;
import viewable.towers.Tower;
import viewable.towers.TowerType;

public class TowerDefenseBoard extends Observable{
	private static final int BOARD_WIDTH = 10;
	private static final int BOARD_LENGTH = 10;
	
	private Viewable[][] board;
	public TowerDefenseBoard(TowerDefenseView view) {
		board = new Viewable[BOARD_WIDTH][BOARD_LENGTH];
		addObserver(view);
	}
	
	public void addTower(int row, int col, TowerType type){
		switch(type) {
			case BASICTOWER:
				board[col][row] = new ArcherTower();
				break;
			default:
				break;
		}
		setChanged();
		notifyObservers(row+" "+col);
	}
	
	public Viewable[][] getBoard() {
		return board;
	}
}
