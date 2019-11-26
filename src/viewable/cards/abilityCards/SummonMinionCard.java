package viewable.cards.abilityCards;

import viewable.cards.Card;
import viewable.gameObjects.Player;

public class SummonMinionCard extends Card {
	private static final int cardId 			= 8;
	private static final String cardName 		= "Summon Minions";
	private static final int mininonsToSummon	= 2;
	private static final int cardCost       = 1;
	public SummonMinionCard() {
		super(cardName, cardId, cardCost);
	}
	
	@Override
	public void Ability(Player p) {
	}
}
