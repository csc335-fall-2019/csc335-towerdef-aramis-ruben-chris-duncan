package handlers;

import game.TowerDefenseController;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import viewable.Viewable;
import viewable.cards.towers.TowerCard;
import viewable.gameObjects.Player;
import viewable.gameObjects.TowerType;

public class GameObjectClickedHandler implements EventHandler<MouseEvent>{
	
	private int row;
	private int col;
	private Player player;
	private TowerDefenseController controller;
	private Viewable view;
	
	public GameObjectClickedHandler(Viewable view, int col, int row, Player player, TowerDefenseController controller) {
		this.view = view;
		this.row = row;
		this.col = col;
		this.player = player;
		this.controller = controller;
	}
	
	@Override
	public void handle(MouseEvent e) {
		if(player.getSelectedCard()!=null && player.getSelectedCard() instanceof TowerCard) {
			TowerType vals = null;
			for(TowerType t: TowerType.values()) {
				if(t.getTower() == ((TowerCard) player.getSelectedCard()).getTower()) {
					vals = t;
				}
			}
			System.out.println(vals);
			controller.addTower(row, col, vals);
			player.addToDiscard(player.getSelectedCard());
			player.setSelectedCard(null);
			player.drawCards(1);
		}
	}
}
