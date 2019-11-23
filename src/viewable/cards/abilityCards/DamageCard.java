package viewable.cards.abilityCards;

import viewable.cards.Card;
import viewable.gameObjects.Player;

// deals 1 damage to opposing player
// should be a high cost card and appear infrequently in market

public class DamageCard extends Card {
	
	private static final int cardId = 10;
	private static final String cardName = "Bombing Pigeon";
	private static final int cardCost = 1;
	private static final int damageAmount = 1;

	public DamageCard(String name, int id, int cost) {
		super(name, id, cost);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Ability(Player p) {
		// TODO Auto-generated method stub
		
	}

}
