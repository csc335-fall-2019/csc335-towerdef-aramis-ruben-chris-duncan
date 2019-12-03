package game;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import handlers.ExitHandler;
import handlers.MapEditorHandler;
import handlers.NewGameHandler;
import handlers.PanHandler;
import handlers.SoundHandler;
import handlers.VideoHandler;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import viewable.Viewable;
import viewable.gameObjects.TowerType;
import viewable.gameObjects.WaveGenerator;
import viewable.mapObjects.Path;

public class TowerDefenseView extends Application implements Observer{
	public static Stage MESSAGE_RECEIVED;
	
	private static final int VIEWABLE_ROWS = 13;
	private static final int VIEWABLE_COLS = 34;
	private static final int SIZE_IMAGE = 47;
	private Stage stage;
	private TowerDefenseController controller;
	private ViewModel model;
	private GridPane grid;
	private int round;
	private WaveGenerator wave;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Initial Set Up
		round = 0;
		wave = new WaveGenerator();
		controller = new TowerDefenseController(this);
		model = new ViewModel(1080,1920);
		stage = primaryStage;
		
		// Set Up Other Player Area
		HBox top = createTop();
		
		// Set Up Primary Player
		HBox bottom = createBottom();
		// Set Up Market
		VBox market = createMarket();
		setupGrid();
		//StackPane stack = new StackPane();
		//stack.getChildren().add(pane);
		//stack.getChildren().add(createChatBottom(p2p));
		//stack.setPickOnBounds(false);
		// Set Up Menu Bar
		MenuBar menu = createMenuBar();
		setupGrid();
		
		BorderPane root = new BorderPane();
		BorderPane pane = new BorderPane();
		root.setCenter(pane);
		root.setLeft(market);
		root.setTop(menu);
		pane.setCenter(grid);
		pane.setTop(top);
		pane.setBottom(bottom);

		primaryStage.setScene(new Scene(root, model.getWidth(), model.getHeight()));	
		
		primaryStage.getScene().setOnMouseMoved(new PanHandler(model, primaryStage));
		primaryStage.setResizable(false);
		primaryStage.setFullScreen(true);
		primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		//loadMusic();
		primaryStage.show();
		primaryStage.sizeToScene();
	}
	
	private void setupGrid() throws IOException {
		// Set Up Grid
		grid = createGrid();
		grid.setStyle("-fx-border-color: black;");
		
		FileInputStream input = new FileInputStream("./resources/images/map1.png");
		Image image = new Image(input);
		BackgroundImage map = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background background = new Background(map);
		grid.setBackground(background);
		input.close();
	}
	
	public GridPane createGrid() {
		GridPane grid = new GridPane();
		for (int i = 0; i < VIEWABLE_COLS; i++) {
			for (int j = 0; j < VIEWABLE_ROWS; j++) {
				Rectangle x = new Rectangle(SIZE_IMAGE, SIZE_IMAGE);
				x.setFill(Color.TRANSPARENT);
				x.setStroke(Color.BLACK);
				x.setStrokeWidth(1);
				int row = j;
				int col = i;
				x.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						Node v = (Node)e.getTarget();
						if(v.getUserData()==null) {
							controller.addTower(row, col, TowerType.BASICTOWER);
							e.consume();
						}else {
							System.out.println("tower");
						}
					}
				});
				grid.add(x, i, j);
			}
		}
		return grid;	
	}

	public void update() {
		BorderPane pane = (BorderPane)stage.getScene().getRoot();
		wave.generateRandom(round); 
		Viewable[][][] map = controller.getBoard();
		Viewable p1Start = null;
		Viewable p2Start = null;
		for (int i = 0; i < map[0].length; i++) {
			if (map[0][i][0] instanceof Path) {
				p1Start = map[0][i][0];
			}
			if (map[map.length-1][i][0] instanceof Path) {
				p2Start = map[map.length - 1][i][0];
			}
		}
		while(controller.canMove()) {
			break;
		}
	}
	
	private HBox createBottom() throws IOException {
		HBox bottom = new HBox();
		bottom.setStyle("-fx-border-color: black;");
		FileInputStream input = new FileInputStream("./resources/images/playmat1.png");
		Image image = new Image(input);
		BackgroundImage player1 = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background background = new Background(player1);
		bottom.setBackground(background);
		
		int prefHeight = 215;
		bottom.setPrefHeight(prefHeight);
		model.addSubHeight(prefHeight);
		
		VBox stat2 = new VBox();
		Label hp2 = new Label("Health: ");
		Label mp2 = new Label("Gold: ");
		stat2.getChildren().add(hp2);
		stat2.getChildren().add(mp2);
		bottom.getChildren().add(stat2);
		input.close();
		return bottom;
	}
	
	private HBox createTop() throws IOException {
		// Set Up Other Player Area
		HBox top = new HBox();
		top.setStyle("-fx-border-color: black;");
		
		FileInputStream input = new FileInputStream("./resources/images/playmat2.png");
		Image image = new Image(input);
		BackgroundImage player2 = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background background = new Background(player2);
		top.setBackground(background);
		int prefHeight = 215;
		top.setPrefHeight(prefHeight);
		
		VBox stat1 = new VBox();
		Label hp1 = new Label("Health: ");
		Label mp1 = new Label("Gold: ");
		stat1.getChildren().add(hp1);
		stat1.getChildren().add(mp1);
		top.getChildren().add(stat1);
		
		model.addSubHeight(prefHeight);
		input.close();
		return top;
	}
	
	private VBox createMarket() throws IOException {
		// Set Up Market
		VBox market = new VBox();
		market.setStyle("-fx-border-color: black;");
		
		FileInputStream input = new FileInputStream("./resources/images/market.png");
		Image image = new Image(input);
		BackgroundImage marketBg = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background background = new Background(marketBg);
		market.setBackground(background);
		int prefWidth = 350;
		model.addSubWidth(prefWidth);
		market.setPrefWidth(prefWidth);
		input.close();
		return market;
	}
	
	private ImageView getResource(Viewable obj, int row, int col) throws FileNotFoundException {
		ImageView view;
		if(obj == null) {
			view = new ImageView(new Image(new FileInputStream(Viewable.getDefaultResource())));
			view.setUserData(obj);
		}else {
			view = new ImageView(new Image(new FileInputStream(obj.getResource())));
			view.setUserData(obj);
		}
		view.setFitHeight(SIZE_IMAGE);
		view.setFitWidth(SIZE_IMAGE);
		
		view.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ImageView v = (ImageView)e.getTarget();
				if(v.getUserData()==null) {
					controller.addTower(row, col, TowerType.BASICTOWER);
					e.consume();
				}else {
					System.out.println("tower");
				}
			}
		});
		return view;
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
		model.addSubHeight(menuHeight);
		return bar;
	}
	
	private Menu createFileMenu() {
		// Create the file menu option, will hold save and exit commands.
		Menu file = new Menu();
		
		MenuItem newGame = new MenuItem();
		newGame.setText("New Game");
		newGame.setOnAction(new NewGameHandler());
		
		MenuItem mapEditor = new MenuItem();
		mapEditor.setText("Open Map Editor");
		mapEditor.setOnAction(new MapEditorHandler());
		
		MenuItem exit = new MenuItem();
		exit.setText("Exit");
		exit.setOnAction(new ExitHandler());
		
		file.getItems().addAll(newGame, mapEditor, exit);
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

	private void loadMusic() {
		try {
			String randomMusic = findRandomMusic();
			Media music = new Media(randomMusic);
			MediaPlayer player = new MediaPlayer(music);
			player.setOnEndOfMedia(new Runnable() {
				@Override
				public void run() {
					String randomMusic = "";
					while(randomMusic.equalsIgnoreCase("")) {
						try {
							randomMusic = findRandomMusic();
							break;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					Media music = new Media(randomMusic);
					MediaPlayer player = new MediaPlayer(music);
					player.play();
					
				}
			});
			player.play();
		}catch(Exception ex) {
			// Show modal.
			System.out.println(ex.getMessage());
		}
	}
	
	private String findRandomMusic() throws IOException, URISyntaxException {
		File file = new File("./resources/music");
		List<File> files = new ArrayList<File>();
		for(File f: file.listFiles()) {
			files.add(f);
		}
		Collections.shuffle(files);
		return files.get(0).toURI().toString();
	}

	@Override
	public void update(Observable o, Object e) {
		// TODO Auto-generated method stub
		int i = Integer.parseInt(((String)e).split(" ")[0]);
		int j = Integer.parseInt(((String)e).split(" ")[1]);
		try {
			setBoard(i, j);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void setBoard(int row, int col) throws FileNotFoundException {
		Viewable[][][] board = controller.getBoard();
		int i =0;
		for(int j =0;j<board[col][row].length;j++) {
			if(board[col][row][j]!=null) {
				i=j;
			}
		}
		Viewable obj = board[col][row][i];
		Node node = null;
		Node toRemove = null;
		for(Node n: grid.getChildren()) {
			if(GridPane.getColumnIndex(n)==col&&GridPane.getRowIndex(n)==row) {
				node = getResource(obj, row, col);
			}
		}
		grid.add(node, col, row);
	}
}


