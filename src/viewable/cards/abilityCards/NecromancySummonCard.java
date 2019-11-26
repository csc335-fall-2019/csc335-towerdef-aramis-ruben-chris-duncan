package viewable.cards.abilityCards;

import viewable.cards.Card;
import viewable.gameObjects.Player;

// player pays life to summon minion
// 2 life for 5 normal minions
// maybe 2 life for 1 powerful minion

public class NecromancySummonCard extends Card {
	
	private static final int cardId = 12;
	private static final String cardName = "Necromancy";
	private static final int cardCost = 1;
	private static final int lifeCost = 2;
	private static final int minionsToSummon = 5;

	public NecromancySummonCard() {
		super(cardName, cardId, cardCost);
	}

	@Override
	public void Ability(Player p) {
		p.payLife(lifeCost);
		p.summonMinion(minionsToSummon);
	}

}
