package game;

/**
 * TowerDefenseBoard.java
 * 
 * Handles on the game logic and attribute properites/changes that occur
 * to the main board of the game.
 * 
 * Usage instructions:
 * 
 * Construct TowerDefenseBoard
 * TowerDefenseBoard board = new TowerDefenseBoard(view, controller)
 * 
 * Other useful methods:
 * board.addTower()
 * board.triggerMinions()
 * board.getBoard()
 * board.setBoard()
 * board.getMarket()
 * 
 */

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
	
	/**
     * @purpose: Creates a new board that is observable by the view.
     * 
     * @param view - A TowerDefenseView object that is the window that displays
     * the entire game.
     * 
     * @param controller - the game controller that handles all the internal game 
     * logic.
     */
	public TowerDefenseBoard(TowerDefenseView view, TowerDefenseController controller) throws FileNotFoundException {
		board = new Map();
		market = new Market(view, controller);
		addObserver(view);
	}

	/**
     * @purpose: Adds a tower to the board.
     * 
     * @param row - tells which row the tower was added to.
     * 
     * @param col  - tells which col the tower was added to.
     * 
     * @param type - A TowerType object that tells which kind of tower
     * was added to the grid
     */
	public void addTower(int row, int col, TowerType type){
		Viewable[][][] boardArr = board.getBoard();
		System.out.println(row +" "+col);
		if(type.equals(TowerType.Deleted)) {
			boardArr[col][row][0] = null;
		}else {
			// Trying to add the tower to the grid if it's a illegal placement
			try {
				boardArr[col][row][0] = type.getTower().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		setChanged();
		notifyObservers(row+" "+col);
	}
	
	public void updateBoard(int row, int col) {
		setChanged();
		notifyObservers(row+" "+col);
	}
	
	/**
     * @purpose: A process for generating enemy waves.
     * 
     */
  public void triggerMinions() {
		setChanged();
		notifyObservers(true);
	}
  
  	public void beginningOfTurn() {
  		setChanged();
  		notifyObservers(false);
  	}
  
	public TowerDefenseBoard flip() {
		TowerDefenseBoard tdBoard = new TowerDefenseBoard();
		tdBoard.setBoard(board.flip());
		tdBoard.setMarket(market);
		System.out.println(tdBoard);
		return tdBoard;
	}
	

	public void setView(TowerDefenseView view) {
		addObserver(view);
	}
	
	/**
     * @purpose: Getter method for the board.
     * 
     */
	public Map getBoard() {
		return board;
	}
	
	/**
     * @purpose: Setter method for board attribute.
     * 
     */
	public void setBoard(Map m) {
		board = m;
		System.out.println("Updating view.");
		setChanged();
		notifyObservers(m);
	}
	
	/**
     * @purpose: Getter method for the market.
     * 
     */
	public Market getMarket() {
		return market;
	}
	
	private void setMarket(Market m) {
		market = m;
	}
}
