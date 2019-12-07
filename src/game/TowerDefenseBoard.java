package game;
import java.io.FileNotFoundException;
import java.util.Observable;

import viewable.Viewable;
import viewable.gameObjects.Map;
import viewable.gameObjects.Market;
import viewable.gameObjects.TowerType;

public class TowerDefenseBoard extends Observable{	
	private Map board;
	private Market market;
	public TowerDefenseBoard(TowerDefenseView view) throws FileNotFoundException {
		board = new Map();
		market = new Market();
		addObserver(view);
	}

	public void addTower(int row, int col, TowerType type){
		Viewable[][][] boardArr = board.getBoard();
		System.out.println(row +" "+col);
		try {
			boardArr[col][row][0] = type.getTower().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setChanged();
		notifyObservers(row+" "+col);
	}
	
	public Map getBoard() {
		return board;
	}
	
	public void setBoard(Map m) {
		board = m;
		setChanged();
		notifyObservers(m);
	}
	
	public Market getMarket() {
		return market;
	}
}
