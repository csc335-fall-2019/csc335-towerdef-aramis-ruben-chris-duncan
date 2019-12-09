package network;

import viewable.cards.Card;

public class MarketCardRemovedMessage extends TowerDefenseMoveMessage{
	private Card card;
	public MarketCardRemovedMessage(Card card) {
		this.card = card;
	}
	
	public Card getCard() {
		return card;
	}
}
