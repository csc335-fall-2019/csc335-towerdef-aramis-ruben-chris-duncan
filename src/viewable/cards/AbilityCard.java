package viewable.cards;

import viewable.Viewable;
import viewable.gameObjects.Player;

public abstract class AbilityCard extends Viewable {
	private String name;
	private int id;
	private int cost;
	
	public AbilityCard(String name, int id, int cost) {
		this.name = name;
		this.id = id;
		this.cost = cost;
	}
	
	public abstract void ability(Player p);
	
	public String getName() {
		return this.name;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getCost() {
		return this.cost;
	}

}
