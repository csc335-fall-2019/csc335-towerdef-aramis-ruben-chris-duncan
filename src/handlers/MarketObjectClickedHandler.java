package handlers;

import game.TowerDefenseView;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import viewable.cards.Card;
import viewable.gameObjects.Market;

public class MarketObjectClickedHandler implements EventHandler<MouseEvent>{
	private Card card;
	private Market market;
	private TowerDefenseView view;
	
	public MarketObjectClickedHandler(Card card, Market market, TowerDefenseView view) {
		this.card = card;
		this.market = market;
		this.view  = view;
	}

	@Override
	public void handle(MouseEvent arg0) {
		if(arg0.getClickCount()<2) {
			return;
		}
		if(!market.removeFromForSale(card)) {
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
}
