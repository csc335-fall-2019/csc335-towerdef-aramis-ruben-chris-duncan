/**
 * @author Ruben Tequida
 * 
 * Initial: Setup and creation of Player object
 */

package viewable.gameObjects;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.List;

import game.TowerDefenseController;
import handlers.CardObjectClickedHandler;
import handlers.GameObjectClickedHandler;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import viewable.Viewable;
import viewable.cards.Card;
import viewable.cards.abilityCards.PlunderCard;
import viewable.cards.towers.ArcherTowerCard;

public class Player implements Serializable{
	
	private IntegerProperty health;
	private ListProperty<ImageView> hand;
	private java.util.Map<Card, ImageView> mapCards;
	private Deck draw;
	private Deck discard;
	private IntegerProperty gold;
	private Card selectedCard;
	private int goldMultiplier;
	private transient TowerDefenseController controller;
	private volatile boolean turnComplete;
	
	public Player(TowerDefenseController controller) throws FileNotFoundException {
		this.controller = controller;
		turnComplete = false;
		health = new SimpleIntegerProperty(20);
		mapCards = new HashMap<Card, ImageView>();
		ObservableList<ImageView> observableList = FXCollections.observableArrayList(new ArrayList<ImageView>());
		hand = new SimpleListProperty<ImageView>(observableList);
		draw = new Deck();
		discard = new Deck();
		gold = new SimpleIntegerProperty(20);
		goldMultiplier = 1;
		for (int i = 0; i < 6; i++) {
			draw.add(new ArcherTowerCard());
		}
		for (int i = 0; i < 4; i++) {
			draw.add(new PlunderCard());
		}
		draw.shuffle();
		for (int i = 0; i < 5; i++) {
			Card c = draw.drawCard();
			if(c==null) {
				continue;
			}
			ImageView view = getResource(c);
			hand.add(view);
			mapCards.put(c, view);
		}
		Collections.shuffle(hand);
	}
	
	private ImageView getResource(Card obj) throws FileNotFoundException {
		ImageView view;
		if(obj == null) {
			view = new ImageView(new Image(new FileInputStream(Viewable.getDefaultResource())));
		}else {
			view = new ImageView(new Image(new FileInputStream(obj.getResource())));
		}
		
		view.setFitHeight(196);
		view.setFitWidth(128);
		
		view.setOnMouseClicked(new CardObjectClickedHandler(obj, controller));
		
		return view;
	}
	
	public ObservableList<ImageView> getHand(){
		return hand;
	}
	
	public void addToDiscard(Card card) {
		hand.removeAll(mapCards.get(card));
		discard.add(card);
		hand.remove(card);
		mapCards.remove(card);
	}
	
	public void resetDraw() {
		for (Card card : discard.getDeck()) {
			draw.add(card);
		}
		draw.shuffle();
		discard.empty();
	}
	
	public void drawCards(int x) throws FileNotFoundException {
		for (int i = 0; i < x; i++) {
			if (draw.isEmpty()) {
				resetDraw();
			}
			Card c = draw.drawCard();
			if(c==null) {
				continue;
			}
			ImageView view = getResource(c);
			hand.add(view);
			mapCards.put(c, view);
		}
	}
	
	public void discardHand() {
		for (Card c : mapCards.keySet()) {
			discard.add(c);
			hand.remove(mapCards.get(c));
		}
		mapCards.clear();
	}
	
	public boolean isFinished() {
		return turnComplete;
	}
	
	public void setComplete(boolean b) {
		turnComplete = b;
	}
	
	public void increaseGold(int amount) {
		gold.setValue((amount * goldMultiplier)+getGold());
	}
	
	public void gainLife(int amount) {
		health.setValue(amount+getHealth());
	}
	
	public void payLife(int amount) {
		health.setValue(getHealth()-amount);
	}
	
	public void summonMinion(int amount) {
		// TODO
	}
	
	public void summonBigMinion() {
		// TODO
	}
	
	public void buffReward() {
		goldMultiplier = 2;
	}
	
	public void damageOther(int amount) {
		health.set(health.intValue() - amount);
	}
	
	public int getGold() {
		return gold.intValue();
	}
 	
	public void damageTaken(int amount) {
		health.setValue(getHealth()-amount);
	}
	
	public int getHealth() {
		return health.intValue();
	}

	public void setSelectedCard(Card s) {
		selectedCard = s;
	}
	
	public Card getSelectedCard() {
		return selectedCard;
	}
	
	public IntegerProperty getViewableHealth() {
		return health;
	}
	
	public IntegerProperty getViewableGold() {
		return gold;
	}
	
	public Deck getDiscard() {
		return discard;
	}
	
	public java.util.Map<Card, ImageView> getCardHand() {
		return mapCards;
	}
	
	public void printCards(Deck deck) {
		List<Card> cards = deck.getDeckAsList();
		System.out.println(cards);
	}
}
