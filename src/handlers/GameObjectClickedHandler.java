package handlers;

import java.io.FileNotFoundException;

import game.TowerDefenseController;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import viewable.Viewable;
import viewable.cards.Card;
import viewable.cards.towers.TowerCard;
import viewable.gameObjects.Player;
import viewable.gameObjects.Tower;
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
			if (controller.isTower(row, col)) {
				String tower = controller.getTowerName(row, col);
				String cardType = ((TowerCard) player.getSelectedCard()).getTower().toString();
				String[] split = cardType.split("\\.");
				if (tower.equals(split[2])) {
					TowerCard c = (TowerCard) player.getSelectedCard();
					c.Upgrade((Tower)view);
					System.out.println(view.getResource());
					try {
						((ImageView) e.getTarget()).setImage(ImageResourceLoadingHandler.getResource(view).getImage());
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					player.addToDiscard(player.getSelectedCard());
					player.setSelectedCard(null);
				}
			} else {
				TowerType vals = null;
				for(TowerType t: TowerType.values()) {
					if(t.getTower() == ((TowerCard) player.getSelectedCard()).getTower()) {
						vals = t;
					}
				}
				if(vals==null) {
					return;
				}
				controller.addTower(row, col, vals);
				player.addToDiscard(player.getSelectedCard());
				player.setSelectedCard(null);
			}
		}
	}
}
