/**
 * @author Ruben Tequida
 * 
 * Initial: Setup and creation of Player object
 */

package viewable.gameObjects;
import java.util.ArrayList;
import java.util.List;

import viewable.cards.*;
import viewable.cards.abilityCards.PlunderCard;
import viewable.cards.towers.ArcherTowerCard;

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
		for (int i = 0; i < 6; i++) {
			draw.add(new ArcherTowerCard());
		}
		for (int i = 0; i < 4; i++) {
			draw.add(new PlunderCard());
		}
		for (int i = 0; i < 5; i++) {
			hand.add(draw.drawCard());
		}
	}
	
	public void addToDiscard(Card card) {
		discard.add(card);
	}
	
	public void resetDraw() {
		for (Card card : discard.getDeck()) {
			draw.add(card);
		}
		draw.shuffle();
		discard.empty();
	}
	
	public void drawCards(int x) {
		for (int i = 0; i < x; i++) {
			hand.add(draw.drawCard());
		}
	}
	
	public void increaseGold(int amount) {
		gold += amount;
	}
	
	public void gainLife(int amount) {
		health += amount;
	}
	
	public void payLife(int amount) {
		health -= amount;
	}
	
	public void summonMinion(int amount) {
		// TODO
	}
	
	public void summonBigMinion() {
		// TODO
	}
	
	public void buffReward(int amount) {
		// TODO
	}
	
	public void damageOther(int amount) {
		// TODO: damage other player
	}
}
