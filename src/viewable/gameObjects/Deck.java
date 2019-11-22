package viewable.gameObjects;

import java.util.ArrayList;
import java.util.List;

import viewable.cards.Card;

public class Deck {
	private List<Card> deck;
	
	public Deck() {
		deck = new ArrayList<Card>();
		deck.add(null);
	}
	
	public Deck(int size) {
		deck = new ArrayList<Card>();
		
	}
	
	
}
