package handlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import viewable.cards.Card;
import viewable.gameObjects.Market;

public class MarketObjectClickedHandler implements EventHandler<MouseEvent>{
	private Card card;
	private Market market;
	
	public MarketObjectClickedHandler(Card card, Market market) {
		this.card = card;
		this.market = market;
	}

	@Override
	public void handle(MouseEvent arg0) {
		System.out.println("here");
		market.removeFromForSale(card);
	}

}
