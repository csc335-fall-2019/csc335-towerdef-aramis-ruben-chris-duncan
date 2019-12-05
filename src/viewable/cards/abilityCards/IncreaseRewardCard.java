package viewable.cards.abilityCards;

import java.io.File;
import java.io.IOException;

import viewable.cards.Card;
import viewable.gameObjects.Player;

// increases the gold reward of minions killed
// last a certain amount of time or # minions killed

public class IncreaseRewardCard extends AbilityCard {
	
	private static final int cardId = 11;
	private static final String cardName = "Plentiful Bounty";
	private static final int cardCost = 1;
	private static final int minionsWithBonus = 5;

	public IncreaseRewardCard() {
		super(cardName, cardId, cardCost);
	}

	@Override
	public void ability(Player p) {
		// TODO Auto-generated method stub
		p.buffReward(minionsWithBonus);
	}
	
	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/increaseRewardCard.png")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

}
