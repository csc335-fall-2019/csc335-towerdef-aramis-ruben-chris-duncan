package game;

import java.io.FileInputStream;
import java.util.Observable;
import java.util.Observer;

import handlers.ExitHandler;
import handlers.NewGameHandler;
import handlers.PanHandler;
import handlers.SoundHandler;
import handlers.VideoHandler;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TowerDefenseView2 extends Application implements Observer{
	private Stage stage;
	private TowerDefenseController controller;
	private ViewModel model;
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Initial Set Up
		controller = new TowerDefenseController(this);
		model = new ViewModel(1080,1920, this);
		stage = primaryStage;
		AnchorPane root = new AnchorPane();
		
		// Set Up Other Player Area
		HBox top = new HBox();
		top.setLayoutX(288);
		top.setLayoutY(1);
		top.setPrefHeight(207);
		top.setPrefWidth(1632);
		top.setStyle("-fx-border-color: black;");
		
		FileInputStream input = new FileInputStream("./resources/images/playmat2.png");
		Image image = new Image(input);
		BackgroundImage player2 = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background background = new Background(player2);
		top.setBackground(background);
		
		AnchorPane.setRightAnchor(top, 0.0);
		AnchorPane.setTopAnchor(top, 25.0);
		
		VBox stat1 = new VBox();
		Label hp1 = new Label("Health: ");
		Label mp1 = new Label("Gold: ");
		stat1.getChildren().add(hp1);
		stat1.getChildren().add(mp1);
		top.getChildren().add(stat1);
		
		// Set Up Primary Player
		HBox bottom = new HBox();
		bottom.setLayoutX(288);
		bottom.setLayoutY(873);
		bottom.setPrefHeight(207);
		bottom.setPrefWidth(1632);
		bottom.setStyle("-fx-border-color: black;");
		input = new FileInputStream("./resources/images/playmat1.png");
		image = new Image(input);
		BackgroundImage player1 = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		background = new Background(player1);
		bottom.setBackground(background);
		AnchorPane.setRightAnchor(bottom, 0.0);
		AnchorPane.setBottomAnchor(bottom, 0.0);
		
		VBox stat2 = new VBox();
		Label hp2 = new Label("Health: ");
		Label mp2 = new Label("Gold: ");
		stat2.getChildren().add(hp2);
		stat2.getChildren().add(mp2);
		bottom.getChildren().add(stat2);
		//bottom.setPrefHeight(400);
		
		
		// Set Up Market
		VBox market = new VBox();
		market.setLayoutX(0);
		market.setLayoutY(0);
		market.setPrefHeight(1055);
		market.setPrefWidth(288);
		market.setStyle("-fx-border-color: black;");
		
		input = new FileInputStream("./resources/images/market.png");
		image = new Image(input);
		BackgroundImage marketBg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		background = new Background(marketBg);
		market.setBackground(background);

		AnchorPane.setLeftAnchor(market, 0.0);
		AnchorPane.setBottomAnchor(market, 0.0);
		AnchorPane.setTopAnchor(market, 25.0);
		
		// Set Up Grid
		//root.setTop(createMenuBar());
		GridPane pane = setUpGrid();
		pane.setLayoutX(288);
		pane.setLayoutY(250);
		pane.setPrefHeight(672);
		pane.setPrefWidth(1632);
		pane.setStyle("-fx-border-color: black;");
		
		input = new FileInputStream("./resources/images/map1.png");
		image = new Image(input);
		BackgroundImage map = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		background = new Background(map);
		pane.setBackground(background);
		input.close();

		AnchorPane.setRightAnchor(pane, 0.0);
		//StackPane stack = new StackPane();
		//stack.getChildren().add(pane);
		//stack.getChildren().add(createChatBottom(p2p));
		//stack.setPickOnBounds(false);
		// Set Up Menu Bar
		MenuBar menu = createMenuBar();
		menu.setLayoutX(0);
		menu.setLayoutY(0);
		menu.setPrefWidth(1920);
		//root.setCenter(stack);
		root.getChildren().add(menu);
		root.getChildren().add(pane);
		root.getChildren().add(top);
		root.getChildren().add(bottom);
		root.getChildren().add(market);

		primaryStage.setScene(new Scene(root, model.getWidth(), model.getHeight() + 150));

		
		primaryStage.getScene().setOnMouseMoved(new PanHandler(model, primaryStage));
		primaryStage.setResizable(false);
		primaryStage.setFullScreen(true);
		primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		//loadMusic();
		primaryStage.show();
		primaryStage.sizeToScene();
		
	}
	
	public GridPane setUpGrid() {
		GridPane grid = new GridPane();
		for (int i = 0; i < 34; i++) {
			for (int j = 0; j < 13; j++) {
				
				Rectangle x = new Rectangle(47, 47);
				x.setFill(Color.TRANSPARENT);
				x.setStroke(Color.BLACK);
				x.setStrokeWidth(1);
				GridPane.setRowIndex(x, j);
				GridPane.setColumnIndex(x, i);
				grid.getChildren().add(x);
			}
		}
		return grid;	
	}
	
	private MenuBar createMenuBar() {
		// Create the menu bar.
		MenuBar bar = new MenuBar();
		
		bar.getMenus().add(createFileMenu());
		bar.getMenus().add(createOptionMenu());
		int menuHeight = 25;
		bar.setMinHeight(menuHeight);
		bar.setPrefHeight(menuHeight);
		bar.setMaxHeight(menuHeight);
		model.setMenuHeight(menuHeight);
		return bar;
	}
	
	private Menu createFileMenu() {
		// Create the file menu option, will hold save and exit commands.
		Menu file = new Menu();
		
		MenuItem newGame = new MenuItem();
		newGame.setText("New Game");
		newGame.setOnAction(new NewGameHandler());
		
		MenuItem exit = new MenuItem();
		exit.setText("Exit");
		exit.setOnAction(new ExitHandler());
		
		file.getItems().add(newGame);
		file.getItems().add(exit);
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
}
