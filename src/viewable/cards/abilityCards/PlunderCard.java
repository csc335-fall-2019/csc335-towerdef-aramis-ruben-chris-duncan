package viewable.cards.abilityCards;

import java.io.File;
import java.io.IOException;

import viewable.cards.AbilityCard;
import viewable.cards.Card;
import viewable.gameObjects.Player;

public class PlunderCard extends AbilityCard {
	private static final int cardId 		= 6;
	private static final String cardName 	= "Plunder";
	private static final int goldToIncrease = 1;
	private static final int cardCost       = 1;
	
	public PlunderCard() {
		super(cardName, cardId, cardCost);
	}
	
	@Override
	public void ability(Player p) {
		p.increaseGold(goldToIncrease);
	}
	
	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/plunderCard.png")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}
}
