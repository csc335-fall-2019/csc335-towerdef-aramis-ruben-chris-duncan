package viewable.cards.abilityCards;

import java.io.File;
import java.io.IOException;

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
	
	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/healCard.png")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

}
