package network;

import viewable.cards.Card;

public class MarketCardRemovedMessage extends TowerDefenseMoveMessage{
	private int index;
	public MarketCardRemovedMessage(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
}
