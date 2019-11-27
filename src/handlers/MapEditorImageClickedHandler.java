package handlers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import viewable.Viewable;
import viewable.gameObjects.Map;
import viewable.mapObjects.Path;
import viewable.mapObjects.Placeable;

public class MapEditorImageClickedHandler implements EventHandler<MouseEvent>{
	private int col;
	private int row;
	private int[] currentLocation;
	private Viewable[] currentObject;
	private GridPane grid;
	private Map map;
	
	public MapEditorImageClickedHandler(int row, int col, int[] c, Viewable[] cO, GridPane g, Map m) {
		this.col = col;
		this.row = row;
		currentLocation = c;
		currentObject = cO;
		grid = g;
		map = m;
	}
	
	@Override
	public void handle(MouseEvent e) {
		currentLocation[0] = col;
		currentLocation[1] = row;
		System.out.println(currentObject[0]);
		if(currentObject[0]!=null) {
			try {
				grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == col && GridPane.getColumnIndex(node)== row);
				ImageView view = getResource(currentObject[0], 1);
				view.setOnMouseClicked(new MapEditorImageClickedHandler(row, col, currentLocation, currentObject, grid, map));
				if(currentObject[0] instanceof Placeable) {
					map.getBoard()[row][col][0] = new Placeable();
				}else if(currentObject[0] instanceof Path) {
					map.getBoard()[row][col][0] = new Path();
				}
				grid.add(view, row, col);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private ImageView getResource(Viewable obj, int use) throws FileNotFoundException {
		ImageView view;
		if(obj == null) {
			view = new ImageView(new Image(new FileInputStream(Viewable.getDefaultResource())));
			view.setUserData(obj);
		}else {
			view = new ImageView(new Image(new FileInputStream(obj.getResource())));
			view.setUserData(obj);
		}
		if(use==1) {
			view.setFitHeight(48);
			view.setFitWidth(48);
		}else {
			view.setFitHeight(128);
			view.setFitWidth(128);
		}
		return view;
	}
}
