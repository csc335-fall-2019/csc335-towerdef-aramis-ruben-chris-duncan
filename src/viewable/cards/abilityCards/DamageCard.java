package viewable.cards.abilityCards;

import java.io.File;
import java.io.IOException;

import viewable.cards.Card;
import viewable.gameObjects.Player;
import viewable.gameObjects.Tower;

// deals 1 damage to opposing player
// should be a high cost card and appear infrequently in market

public class DamageCard extends AbilityCard {
	
	private static final int cardId = 10;
	private static final String cardName = "Bombing Pigeon";
	private static final int cardCost = 1;
	private static final int damageAmount = 1;

	public DamageCard() {
		super(cardName, cardId, cardCost);
	}

	@Override
	public void ability(Player p) {
		p.damageOther(damageAmount);
	}
	
	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/damageCard.png")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

}
