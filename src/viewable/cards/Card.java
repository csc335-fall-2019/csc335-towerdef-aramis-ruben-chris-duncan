/** @author: Chris Loper
 * 
 * Super class for all cards
 */
package viewable.cards;

import viewable.Viewable;
import viewable.gameObjects.Player;

public abstract class Card extends Viewable {
	private String name;
	private int id;
	private int cost;
	
	public Card(String name, int id, int cost) {
		this.name = name;
		this.id = id;
		this.cost = cost;
	}
	
	public String getName() {
		return this.name;
	}
	
	public abstract void Ability(Player p);
	
	public int getId() {
		return this.id;
	}
	
	public int getCost() {
		return this.cost;
	}
}
