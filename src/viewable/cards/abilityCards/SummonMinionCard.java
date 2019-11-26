package viewable.cards.abilityCards;

import viewable.cards.Card;
import viewable.gameObjects.Player;

public class SummonMinionCard extends Card {
	private static final int cardId 			= 8;
	private static final String cardName 		= "Draw A Card";
	private static final int minionsToSummon	= 1;
	private static final int cardCost       = 1;
	
	public SummonMinionCard() {
		super(cardName, cardId, cardCost);
	}
	
	@Override
	public void Ability(Player p) {
		p.summonMinion(minionsToSummon);
	}
}
