package viewable.cards.abilityCards;

import viewable.Viewable;
import viewable.cards.Card;
import viewable.gameObjects.Player;

public abstract class AbilityCard extends Card {
	
	public AbilityCard(String name, int id, int cost) {
		super(name, id, cost);
	}
	
	public abstract void ability(Player p);
}
