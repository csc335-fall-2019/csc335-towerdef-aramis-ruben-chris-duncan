package viewable.gameObjects;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
	
	public Market() throws FileNotFoundException {
		market = new Deck();
		ObservableList<ImageView> observableList = FXCollections.observableArrayList(new ArrayList<ImageView>());
		forSale = new SimpleListProperty<ImageView>(observableList);
		marketCards = new HashMap<Card, ImageView>();
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
			market.add(new PlunderCard());
		}
		Collections.shuffle(forSale);
	}
	
	public void populateForSale() throws FileNotFoundException {
		int x = 6 - forSale.size();
		for (int i = 0; i < x; i++) {
			Card c = market.drawCard();
			if(c==null) {
				ImageView v = ImageResourceLoadingHandler.getResource(c);
				forSale.addAll(v);
			}else {
				ImageView v = ImageResourceLoadingHandler.getResource(c);
				marketCards.put(c, v);
				v.setOnMouseClicked(new MarketObjectClickedHandler(c, this));
				forSale.addAll(v);
			}
		}
	}
	
	public void removeFromForSale(Card card) {
		ImageView view = marketCards.get(card);
		forSale.remove(view);
	}
	
	public void repopulateForSale() throws FileNotFoundException {
		for (int i = 0; i < 6; i++) {
			if (forSale.get(i) == null) {
				Card c = market.drawCard();
				if(c==null) {
					break;
				}
				ImageView v = ImageResourceLoadingHandler.getResource(c);
				forSale.add(i, v);
			}
		}
	}
	
	public ListProperty<ImageView> getForSale() {
		return forSale;
	}
}
