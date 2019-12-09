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
	private TowerDefenseController controller;
	private Viewable view;
	
	public GameObjectClickedHandler(Viewable view, int col, int row, TowerDefenseController controller) {
		this.view = view;
		this.row = row;
		this.col = col;
		this.controller = controller;
	}
	
	@Override
	public void handle(MouseEvent e) {
		if(col>controller.getBoard().getBoard().length/2) {
			return;
		}
		if(controller.canUpgrade(row, col)) {
			controller.upgradeTower((Tower)view, row, col);
			try {
				((ImageView) e.getTarget()).setImage(ImageResourceLoadingHandler.getResource(view).getImage());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else {
			controller.useTowerCard(row, col);
		}
	}
}
