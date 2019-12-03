package game;
import java.io.FileNotFoundException;
import java.util.Observable;

import viewable.Viewable;
import viewable.gameObjects.ArcherTower;
import viewable.gameObjects.CannonTower;
import viewable.gameObjects.CurrencyTower;
import viewable.gameObjects.FreezeTower;
import viewable.gameObjects.MageTower;
import viewable.gameObjects.Map;
import viewable.gameObjects.Market;
import viewable.gameObjects.MinionTower;
import viewable.gameObjects.Tower;
import viewable.gameObjects.TowerType;

public class TowerDefenseBoard extends Observable{	
	private Map board;
	private Market market;
	public TowerDefenseBoard(TowerDefenseView view) throws FileNotFoundException {
		board = new Map();
		market = new Market();
		addObserver(view);
	}
	
	public TowerDefenseBoard(TowerDefenseView2 view) {
		board = new Map();
		addObserver(view);
	}

	public void addTower(int row, int col, TowerType type){
		Viewable[][][] boardArr = board.getBoard();
		System.out.println(row +" "+col);
		switch(type) {
			case ArcherTower:
				boardArr[col][row][0] = new ArcherTower();
				break;
			case CannonTower:
				boardArr[col][row][0] = new CannonTower();
				break;
			case FreezeTower:
				boardArr[col][row][0] = new FreezeTower();
				break;
			case CurrencyTower:
				boardArr[col][row][0] = new CurrencyTower();
				break;
			case MinionTower:
				boardArr[col][row][0] = new MinionTower();
				break;
			case MageTower:
				boardArr[col][row][0] = new MageTower();
				break;
			default:
				break;
		}
		setChanged();
		notifyObservers(row+" "+col);
	}
	
	public Viewable[][][] getBoard() {
		return board.getBoard();
	}
	
	public Market getMarket() {
		return market;
	}
}
