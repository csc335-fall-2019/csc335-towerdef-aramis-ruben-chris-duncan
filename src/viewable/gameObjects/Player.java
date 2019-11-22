/**
 * @author Ruben Tequida
 * 
 * Initial: Setup and creation of Player object
 */

package viewable.gameObjects;
import java.util.ArrayList;
import java.util.List;

import viewable.cards.*;

public class Player {
	
	private int health;
	private List<Card> hand;
	private Deck draw;
	private Deck discard;
	private int gold;
	
	public Player() {
		health = 20;
		hand = new ArrayList<Card>();
		draw = new Deck();
		discard = new Deck();
		gold = 0;
		for (int i = 0; i < 5; i++) {
			hand.add(draw.drawCard());
		}
	}
	
	public void addToDiscard(Card card) {
		discard.add(card);
	}
}
