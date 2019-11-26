package viewable.cards.abilityCards;

import java.io.File;
import java.io.IOException;

import viewable.cards.Card;
import viewable.gameObjects.Player;

public class SummonMinionCard extends Card {
	private static final int cardId 			= 8;
	private static final String cardName 		= "Summon Minions";
	private static final int minionsToSummon	= 2;
	private static final int cardCost       = 1;
	
	public SummonMinionCard() {
		super(cardName, cardId, cardCost);
	}
	
	@Override
	public void Ability(Player p) {
		p.summonMinion(minionsToSummon);
	}
	
	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/tst.jpeg")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}
}
