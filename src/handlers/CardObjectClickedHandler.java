package handlers;

import java.util.Map;

import game.TowerDefenseController;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import viewable.cards.Card;
import viewable.cards.abilityCards.AbilityCard;
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
		if (!arg0.getButton().equals(MouseButton.PRIMARY)) {
			arg0.consume();
			return;
		} 
		if (arg0.getClickCount() == 1) {
			player.setSelectedCard(card);
		}
		if (arg0.getClickCount() == 2) {
			if (card instanceof AbilityCard) {
				((AbilityCard) card).ability(player);
				player.addToDiscard(card);
			}
		}
	}
}
