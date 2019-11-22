package game;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;

import viewable.Viewable;
import viewable.gameObjects.BasicTower;
import viewable.gameObjects.Tower;
import viewable.gameObjects.TowerType;

public class TowerDefenseBoard extends Observable{
	private static final int BOARD_WIDTH = 10;
	private static final int BOARD_LENGTH = 10;
	
	Viewable[][] board;
	public TowerDefenseBoard(TowerDefenseView view) {
		board = new Viewable[BOARD_WIDTH][BOARD_LENGTH];
		addObserver(view);
	}
	
	public void addTower(int row, int col, TowerType type){
		switch(type) {
			case BASICTOWER:
				board[col][row] = new BasicTower();
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
