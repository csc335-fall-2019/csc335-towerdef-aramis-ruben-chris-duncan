package game;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Observable;

import viewable.Viewable;
import viewable.gameObjects.Map;
import viewable.gameObjects.Market;
import viewable.gameObjects.TowerType;

public class TowerDefenseBoard extends Observable implements Serializable{	
	private Map board;
	private Market market;
	
	private TowerDefenseBoard() {
	}
	
	public TowerDefenseBoard(TowerDefenseView view, TowerDefenseController controller) throws FileNotFoundException {
		board = new Map();
		market = new Market(view, controller);
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
	
	public TowerDefenseBoard flip() {
		TowerDefenseBoard tdBoard = new TowerDefenseBoard();
		tdBoard.setBoard(board.flip());
		tdBoard.setMarket(market);
		System.out.println(tdBoard);
		return tdBoard;
	}
	
	public void triggerMinions() {
		setChanged();
		notifyObservers(true);
	}

	public void setView(TowerDefenseView view) {
		addObserver(view);
	}
	
	public Map getBoard() {
		return board;
	}
	
	public void setBoard(Map m) {
		board = m;
		System.out.println("Updating view.");
		setChanged();
		notifyObservers(m);
	}
	
	public Market getMarket() {
		return market;
	}
	
	private void setMarket(Market m) {
		market = m;
	}
}
