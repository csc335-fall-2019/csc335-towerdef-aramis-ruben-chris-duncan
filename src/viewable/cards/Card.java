package viewable.cards;

import viewable.Viewable;

public abstract class Card extends Viewable {
	private String name;
	private int id;
	
	public Card(String name, int id) {
		this.name = name;
		this.id = id;
	}
}
