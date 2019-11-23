package viewable.cards.abilityCards;

import viewable.cards.Card;
import viewable.gameObjects.Player;

public class DrawCard extends Card {
	private static final int cardId 		= 7;
	private static final String cardName 	= "Draw A Card";
	private static final int cardsToDraw	= 1;
	private static final int cardCost       = 1;
	public DrawCard() {
		super(cardName, cardId, cardCost);
	}
	@Override
	public void Ability(Player p) {
		p.drawCards(cardsToDraw);
	}
}
