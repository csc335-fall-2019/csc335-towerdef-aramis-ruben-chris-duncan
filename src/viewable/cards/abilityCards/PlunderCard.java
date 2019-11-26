package viewable.cards.abilityCards;

import viewable.cards.Card;
import viewable.gameObjects.Player;

public class PlunderCard extends Card {
	private static final int cardId 		= 6;
	private static final String cardName 	= "Plunder";
	private static final int goldToIncrease = 1;
	private static final int cardCost       = 1;
	
	public PlunderCard() {
		super(cardName, cardId, cardCost);
	}
	
	@Override
	public void Ability(Player p) {
		p.increaseGold(goldToIncrease);
	}
}