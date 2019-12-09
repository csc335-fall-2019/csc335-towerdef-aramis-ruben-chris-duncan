package network;

import viewable.cards.Card;

public class AbilityCardUsedMessage extends TowerDefenseMoveMessage{
	private Card card;
	
	public AbilityCardUsedMessage(Card card) {
		this.card = card;
	}
	
	public Card getCard() {
		return card;
	}
}
