package network;

import viewable.gameObjects.TowerType;

public class TowerPlacedMessage extends TowerDefenseMoveMessage{
	private TowerType tower;
	private int row;
	private int col;
	
	public TowerPlacedMessage(TowerType tower, int row, int col) {
		this.tower = tower;
		this.row = row;
		this.col = col;
	}
	
	public TowerType getTower() {
		return tower;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
}
