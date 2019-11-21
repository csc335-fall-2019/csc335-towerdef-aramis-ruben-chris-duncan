import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import towers.BasicTower;
import towers.Tower;
import towers.TowerType;

public class TowerDefenseBoard {
	private static final int BOARD_WIDTH = 10;
	private static final int BOARD_LENGTH = 10;
	
	Tower[][] towers;
	public TowerDefenseBoard() {
		towers = new Tower[BOARD_WIDTH][BOARD_LENGTH];
	}
	
	public void addTower(int row, int col, TowerType type){
		switch(type) {
			case BASICTOWER:
				towers[col][row] = new BasicTower();
				break;
			default:
				break;
		}
	}
	
	public Tower[][] getBoard() {
		return towers;
	}
}
