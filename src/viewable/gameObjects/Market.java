package viewable.gameObjects;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
		}
		Collections.shuffle(forSale);
	}
	
	public void populateForSale() throws FileNotFoundException {
		int x = 6 - forSale.size();
		for (int i = 0; i < x; i++) {
			Card c = market.drawCard();
			if(c==null) {
				ImageView v = ImageResourceLoadingHandler.getResource(c);
				v.setOnMouseClicked(new MarketObjectClickedHandler(c, this));
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
		Player player = this.view.getCurrentPlayer();
		int cost = card.getCost();
		if (player.getGold() >= cost) {
			player.increaseGold(-cost);
			if(card==null) {
				return;
			}
			ImageView view = marketCards.get(card);
			forSale.remove(view);
			player.addToDiscard(card);
		} else {
			Stage primary = view.getPrimaryStage();
			Stage error = new Stage();
			error.setMinHeight(200);
			error.setMinWidth(470);
			VBox area = new VBox();
			Label l = new Label("Not enough gold to buy this card");
			l.setFont(new Font("Arial", 24));
			l.setTranslateX(50);
			l.setTranslateY(30);
			Button ok = new Button("OK");
			ok.setTranslateX(200);
			ok.setTranslateY(55);
			ok.setOnAction((e)->{
				error.close();
			});
			area.getChildren().add(l);
			area.getChildren().add(ok);
			Scene scene = new Scene(area);
			error.setScene(scene);
			error.initOwner(primary);
			error.initModality(Modality.APPLICATION_MODAL);
			error.showAndWait();
		}
	}
	
	public void repopulateForSale() throws FileNotFoundException {
		int size = forSale.getSize();
		for (int i = size; i < 6; i++) {
			Card c = market.drawCard();
			ImageView v = ImageResourceLoadingHandler.getResource(c);
			v.setOnMouseClicked(new MarketObjectClickedHandler(c, this));
			marketCards.put(c, v);
			forSale.add(i, v);
		}
	}
	
	public ListProperty<ImageView> getForSale() {
		return forSale;
	}
	
	public void setView(TowerDefenseView view) {
		this.view = view;
	}
}
