package viewable.cards.abilityCards;

import viewable.cards.Card;
import viewable.gameObjects.Player;

// heals 1 life

public class HealCard extends Card {
	
	private static final int cardId = 9;
	private static final String cardName = "Heal";
	private static final int cardCost = 1;
	private static final int healAmount = 1;

	public HealCard() {
		super(cardName, cardId, cardCost);
	}

	@Override
	public void Ability(Player p) {
		p.gainLife(healAmount);
	}

}
