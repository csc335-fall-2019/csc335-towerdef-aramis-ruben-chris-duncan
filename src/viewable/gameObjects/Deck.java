/**
 * @author Aramis Sennyey
 * @author Ruben Tequida
 * 
 * rt update - adding drawCard and shuffle function
 */

package viewable.gameObjects;

import java.util.ArrayList;
import java.util.Collections;
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
	
	public Card drawCard() {
		Card card = deck.get(0);
		deck.remove(0);
		return card;
	}
	
	private void shuffle() {
		Collections.shuffle(deck);
	}
	
	public void add(Card card) {
		deck.add(card);
	}
	
	public boolean isEmpty() {
		return false;
	}
	
}