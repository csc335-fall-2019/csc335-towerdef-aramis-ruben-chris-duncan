/**
 * @purpose: this class contains the market 
 */

package viewable.gameObjects;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import game.TowerDefenseController;
import game.TowerDefenseView;
import handlers.ImageResourceLoadingHandler;
import handlers.MarketObjectClickedHandler;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import viewable.cards.*;
import viewable.cards.abilityCards.DamageCard;
import viewable.cards.abilityCards.DrawCard;
import viewable.cards.abilityCards.HealCard;
import viewable.cards.abilityCards.IncreaseRewardCard;
import viewable.cards.abilityCards.NecromancySummonCard;
import viewable.cards.abilityCards.PlunderCard;
import viewable.cards.abilityCards.SummonMinionCard;
import viewable.cards.towers.CannonTowerCard;
import viewable.cards.towers.CurrencyTowerCard;
import viewable.cards.towers.FreezeTowerCard;
import viewable.cards.towers.MageTowerCard;
import viewable.cards.towers.MinionTowerCard;

public class Market {
	
	private Deck market;
	private ListProperty<ImageView> forSale;
	private java.util.Map<Card, ImageView> marketCards;
	private TowerDefenseView view;
	private TowerDefenseController controller;
	
	/**
	 * @purpose: constructor the market; initializes a deck for the market,
	 * sets the view and the controller, fills the market with cards to be bought,
	 * 
	 * @param view: the view for the game
	 * 
	 * @param controller: the controller for the game
	 * 
	 * @throws FileNotFoundException: throws exception if the images can not be found
	 */
	public Market(TowerDefenseView view, TowerDefenseController controller) throws FileNotFoundException {
		market = new Deck();
		this.view = view;
		this.controller = controller;
		ObservableList<ImageView> observableList = FXCollections.observableArrayList(new ArrayList<ImageView>());
		forSale = new SimpleListProperty<ImageView>(observableList);
		marketCards = new HashMap<Card, ImageView>();
		fillMarket();
		market.shuffle();
		populateForSale();
	}

	/**
	 * @purpose: fills the market deck with cards to be bought
	 */
	private void fillMarket() {
		for (int i = 0; i < 4; i++) {
			market.add(new SummonMinionCard());
			market.add(new CannonTowerCard());
			market.add(new CurrencyTowerCard());
			market.add(new FreezeTowerCard());
			market.add(new MageTowerCard());
			market.add(new MinionTowerCard());
			market.add(new DamageCard());
			market.add(new DrawCard());
			market.add(new HealCard());
			market.add(new IncreaseRewardCard());
			market.add(new NecromancySummonCard());
		}
		Collections.shuffle(forSale);
	}
	
	/**
	 * @purpose: populates the 6 cards that are available to be bought in the market
	 * 
	 * @throws FileNotFoundException: throws exception if card arts can not be found
	 */
	public void populateForSale() throws FileNotFoundException {
		int x = 6 - forSale.size();
		for (int i = 0; i < x; i++) {
			Card c = market.drawCard();
			if(c==null) {
				ImageView v = ImageResourceLoadingHandler.getResource(c);
				v.setOnMouseClicked(new MarketObjectClickedHandler(c, this, view));
				forSale.addAll(v);
			}else {
				ImageView v = ImageResourceLoadingHandler.getResource(c);
				marketCards.put(c, v);
				v.setOnMouseClicked(new MarketObjectClickedHandler(c, this, view));
				forSale.addAll(v);
			}
		}
	}
	
	/**
	 * @purpose: removes a card from currently displayed market cards after it is 
	 * purchased by a player
	 * 
	 * @param card: card object 
	 * 
	 * @return: a boolean if the card is removed or not
	 */
	public boolean removeFromForSale(Card card) {
		Player player = controller.getPlayer();
		int cost = card.getCost();
		if (player.getGold() >= cost) {
			player.increaseGold(-cost);
			if(card==null) {
				return true;
			}
			ImageView view = marketCards.get(card);
			forSale.remove(view);
			player.addToDiscard(card);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @purpose: repopulates the current market with the number of cards that had been
	 * purchased by the player
	 * 
	 * @throws FileNotFoundException: throws exception if resource is not found
	 */
	public void repopulateForSale() throws FileNotFoundException {
		int size = forSale.getSize();
		for (int i = size; i < 6; i++) {
			Card c = market.drawCard();
			ImageView v = ImageResourceLoadingHandler.getResource(c);
			v.setOnMouseClicked(new MarketObjectClickedHandler(c, this, view));
			marketCards.put(c, v);
			forSale.add(i, v);
		}
	}
	
	public ListProperty<ImageView> getForSale() {
		return forSale;
	}
}
