package viewable.gameObjects;

import java.io.FileNotFoundException;
import java.io.Serializable;
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
import javafx.scene.image.ImageView;
import viewable.cards.*;
import viewable.cards.abilityCards.DamageCard;
import viewable.cards.abilityCards.DrawCard;
import viewable.cards.abilityCards.HealCard;
import viewable.cards.abilityCards.IncreaseRewardCard;
import viewable.cards.abilityCards.NecromancySummonCard;
import viewable.cards.abilityCards.SummonMinionCard;
import viewable.cards.towers.CannonTowerCard;
import viewable.cards.towers.CurrencyTowerCard;
import viewable.cards.towers.FreezeTowerCard;
import viewable.cards.towers.MageTowerCard;
import viewable.cards.towers.MinionTowerCard;

public class Market implements Serializable{
	
	private Deck market;
	private List<Card> cards;
	private transient ListProperty<ImageView> forSale;
	private transient java.util.Map<Card, ImageView> marketCards;
	private transient TowerDefenseView view;
	private transient TowerDefenseController controller;
	
	public Market(TowerDefenseView view, TowerDefenseController controller) throws FileNotFoundException {
		market = new Deck();
		this.view = view;
		this.controller = controller;
		ObservableList<ImageView> observableList = FXCollections.observableArrayList(new ArrayList<ImageView>());
		forSale = new SimpleListProperty<ImageView>(observableList);
		marketCards = new HashMap<Card, ImageView>();
		cards = new ArrayList<Card>();
		fillMarket();
		market.shuffle();
		populateForSale();
	}

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
	
	public void populateForSale() throws FileNotFoundException {
		int x = 6 - forSale.size();
		for (int i = 0; i < x; i++) {
			Card c = market.drawCard();
			if(c==null) {
				ImageView v = ImageResourceLoadingHandler.getResource(c);
				v.setOnMouseClicked(new MarketObjectClickedHandler(c, controller, view));
				forSale.addAll(v);
			}else {
				ImageView v = ImageResourceLoadingHandler.getResource(c);
				marketCards.put(c, v);
				cards.add(c);
				v.setOnMouseClicked(new MarketObjectClickedHandler(c, controller, view));
				forSale.addAll(v);
			}
		}
	}
	
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
	
	public void repopulateForSale() throws FileNotFoundException {
		int size = forSale.getSize();
		
		for (int i = size; i < 6; i++) {
			Card c = market.drawCard();
			ImageView v = ImageResourceLoadingHandler.getResource(c);
			v.setOnMouseClicked(new MarketObjectClickedHandler(c, controller, view));
			marketCards.put(c, v);
			forSale.add(i, v);
		}
	}
	
	public ListProperty<ImageView> getForSale() {
		return forSale;
	}
	
	public void repopulateImages() throws FileNotFoundException {
		marketCards = new HashMap<Card, ImageView>();
		ObservableList<ImageView> observableList = FXCollections.observableArrayList(new ArrayList<ImageView>());
		forSale = new SimpleListProperty<ImageView>(observableList);
		for(Card c: cards) {
			ImageView v = ImageResourceLoadingHandler.getResource(c);
			v.setOnMouseClicked(new MarketObjectClickedHandler(c, controller, view));
			marketCards.put(c, v);
			forSale.add(v);
		}
	}
	
	public void setController(TowerDefenseController controller) {
		this.controller = controller;
	}
	
	public void setView(TowerDefenseView view) {
		this.view = view;
	}
}
