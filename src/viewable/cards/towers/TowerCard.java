package viewable.cards.towers;

import viewable.cards.Card;
import viewable.gameObjects.Tower;

public abstract class TowerCard extends Card{

	public TowerCard(String name, int id, int cost) {
		super(name, id, cost);
		// TODO Auto-generated constructor stub
	}

	public abstract Class<? extends Tower> getTower();
}
