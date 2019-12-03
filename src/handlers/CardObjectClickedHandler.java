package handlers;

import game.TowerDefenseController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import viewable.cards.Card;
import viewable.gameObjects.Player;

public class CardObjectClickedHandler implements EventHandler<MouseEvent>{
	private Card card;
	private Player player;
	
	public CardObjectClickedHandler(Card card, Player player) {
		this.card = card;
		this.player = player;
	}

	@Override
	public void handle(MouseEvent arg0) {
		player.setSelectedCard(card);
	}
}
