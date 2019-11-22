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
	
	public Card(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public abstract void Ability(Player p);
	
	public int getId() {
		return this.id;
	}
}
