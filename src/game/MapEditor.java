package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import handlers.ExitHandler;
import handlers.MapEditorImageClickedHandler;
import handlers.SoundHandler;
import handlers.VideoHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import viewable.Viewable;
import viewable.gameObjects.Map;
import viewable.mapObjects.Path;
import viewable.mapObjects.Placeable;

public class MapEditor {
	private Map map;
	private GridPane grid;
	private int[] currentLocation = new int[2];
	private Viewable[] currentObject = new Viewable[1];	
	public Stage create() throws FileNotFoundException {
		Stage stage = new Stage();
		BorderPane root = new BorderPane();
		
		grid = createGrid();
		
		root.setCenter(grid);
		root.setTop(createMenuBar());
		root.setBottom(createTowerMenu());
		
		stage.setScene(new Scene(root, 1920, 1080));
		
		return stage;
	}
	
	private void repaintGrid() throws FileNotFoundException {
		grid.getChildren().clear();
		Viewable[][][] board = map.getBoard();
		for(int i =0;i<board.length;i++) {
			for(int j = 0;j<board[i].length;j++) {
				int col = j;
				int row = i;
				ImageView view = getResource(board[i][j][0], 1);
				view.setOnMouseClicked(new MapEditorImageClickedHandler(row, col, currentLocation, currentObject, grid, map));
				grid.add(view , i, j);
			}
		}
	}
	
	private TilePane createTowerMenu() throws FileNotFoundException {
		TilePane pane = new TilePane();
		Viewable[] towers = new Viewable[] {new Path(), new Placeable()};
		for(Viewable v : towers) {
			ImageView view = getResource(v,2);
			view.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					currentObject[0] = v;
					System.out.println(v.getResource());
				}
			});
			pane.getChildren().add(view);
		}
		return pane;
	}
	
	private MenuBar createMenuBar() {
		// Create the menu bar.
		MenuBar bar = new MenuBar();
		
		bar.getMenus().add(createFileMenu());
		bar.getMenus().add(createOptionMenu());
		int menuHeight = 30;
		bar.setMinHeight(menuHeight);
		bar.setPrefHeight(menuHeight);
		bar.setMaxHeight(menuHeight);
		return bar;
	}
	
	private Menu createFileMenu() {
		// Create the file menu option, will hold save and exit commands.
		Menu file = new Menu();
		
		MenuItem newGame = new MenuItem();
		newGame.setText("Save");
		newGame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					map.save();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		MenuItem load = new MenuItem();
		load.setText("Load");
		load.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					map.load();
					repaintGrid();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		MenuItem exit = new MenuItem();
		exit.setText("Exit");
		exit.setOnAction(new ExitHandler());
		
		file.getItems().addAll(newGame, exit, load);
		file.setText("File");
		return file;
	}
	
	private Menu createOptionMenu() {
		// Create the option menu option, holds sound settings and game visual settings.
		Menu options = new Menu();
		
		MenuItem sound = new MenuItem();
		sound.setText("Sound");
		sound.setOnAction(new SoundHandler());
		
		MenuItem video = new MenuItem();
		video.setText("Video");
		video.setOnAction(new VideoHandler());
		
		options.getItems().add(sound);
		options.getItems().add(video);
		options.setText("Options");
		return options;
	}
	
	private GridPane createGrid() throws FileNotFoundException {
		GridPane grid = new GridPane();
		map = new Map();
		Viewable[][][] board = map.getBoard();
		for(int i =0;i<board.length;i++) {
			for(int j = 0;j<board[i].length;j++) {
				int col = j;
				int row = i;
				ImageView view = getResource(board[i][j][0], 1);
				view.setOnMouseClicked(new MapEditorImageClickedHandler(row, col, currentLocation, currentObject, grid, map));
				grid.add(view , i, j);
			}
		}
		
		return grid;
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
