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
	private TowerDefenseController controller;
	
	public CardObjectClickedHandler(Card card, TowerDefenseController controller) {
		this.card = card;
		this.controller = controller;
	}

	@Override
	public void handle(MouseEvent arg0) {
		if(!controller.hasConnected()||controller.getPlayer().isFinished()||controller.isPaused()) {
			return;
		}
		if (!arg0.getButton().equals(MouseButton.PRIMARY)) {
			arg0.consume();
			return;
		} 
		if (arg0.getClickCount() == 1) {
			controller.setSelectedCard(card);
		}
		if (arg0.getClickCount() == 2) {
			if (card instanceof AbilityCard) {
				controller.useAbilityCard((AbilityCard)card);
			}
		}
	}
}
