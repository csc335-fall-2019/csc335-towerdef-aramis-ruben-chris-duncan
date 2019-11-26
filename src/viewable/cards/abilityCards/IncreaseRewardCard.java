package viewable.cards.abilityCards;

import viewable.cards.Card;
import viewable.gameObjects.Player;

// increases the gold reward of minions killed
// last a certain amount of time or # minions killed

public class IncreaseRewardCard extends Card {
	
	private static final int cardId = 11;
	private static final String cardName = "Plentiful Bounty";
	private static final int cardCost = 1;
	private static final int minionsWithBonus = 5;

	public IncreaseRewardCard() {
		super(cardName, cardId, cardCost);
	}

	@Override
	public void Ability(Player p) {
		// TODO Auto-generated method stub
		p.buffReward(minionsWithBonus);
	}

}