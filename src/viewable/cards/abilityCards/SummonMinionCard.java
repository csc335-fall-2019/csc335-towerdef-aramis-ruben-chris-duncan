package viewable.cards.abilityCards;

import viewable.cards.Card;
import viewable.gameObjects.Player;

public class SummonMinionCard extends Card {
	private static final int cardId 			= 8;
	private static final String cardName 		= "Draw A Card";
	private static final int mininonsToSummon	= 1;
	public SummonMinionCard() {
		super(cardName, cardId);
	}
	
	@Override
	public void Ability(Player p) {
	}
}
