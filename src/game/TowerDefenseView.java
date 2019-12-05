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

import chat.ChatView;
import chat.PeerToPeerSocket;
import handlers.CardObjectClickedHandler;
import handlers.ExitHandler;
import handlers.GameObjectClickedHandler;
import handlers.ImageResourceLoadingHandler;
import handlers.MapEditorHandler;
import handlers.SoundHandler;
import handlers.VideoHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import viewable.Viewable;
import viewable.gameObjects.WaveGenerator;
import viewable.mapObjects.Path;
import viewable.gameObjects.Market;
import viewable.gameObjects.Minion;
import viewable.gameObjects.Player;
import viewable.gameObjects.Tower;

public class TowerDefenseView extends Application implements Observer{
	public static Stage MESSAGE_RECEIVED;
	private static final int SIZE_IMAGE = 47;
	private static final int CARD_WIDTH = 128;
	private static final int CARD_HEIGHT = 196;
	private static final int MINION_MAX_SPEED = 1000;
	private static final int TOWER_MAX_ATTACK_SPEED = 10;
	private Stage stage;
	private TowerDefenseController controller;
	private ViewModel model;
	private GridPane grid;
	private GridPane animationGrid;
	private Pane attackGrid;
	private int round;
	private WaveGenerator wave;
	private Player player;
	private List<ImageView> lsPath;
	private List<Integer> direction;
	private int currentYVal;
	private int currentXVal;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		controller = new TowerDefenseController(this);
		stage = primaryStage;
		currentYVal = 0;
		currentXVal = 0;
		
		Scene scene = new Scene(mainMenu());
		primaryStage.setTitle("Power Tower");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private VBox mainMenu() throws FileNotFoundException {
		VBox vbox = new VBox(25);
		vbox.setPadding(new Insets(20));

		Image logo = new Image(new FileInputStream("./resources/images/splashScreen.gif"));
		ImageView logoView = new ImageView(logo);
		
		HBox buttons = new HBox(15);
		Button newGame = new Button("New Game");
		Button mapEditor = new Button("Map Editor");
		Button exit = new Button("Exit");
		buttons.getChildren().add(newGame);
		buttons.getChildren().add(mapEditor);
		buttons.getChildren().add(exit);
		buttons.setAlignment(Pos.CENTER);
		vbox.getChildren().add(logoView);
		vbox.getChildren().add(buttons);
		
		newGame.setOnAction((e) -> {
			try {
				
				FileChooser fileChooser = new FileChooser();
				
				File initDir = new File("./saves");
				initDir.mkdir();
				
				fileChooser.setInitialDirectory(initDir);
				
				fileChooser.setTitle("Open Resource File");
				File path = fileChooser.showOpenDialog(stage);
				if (path != null) {
					controller.getBoard().load(path.getCanonicalPath());
					newGame();
				}
				
				
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		mapEditor.setOnAction(new MapEditorHandler());
		exit.setOnAction(new ExitHandler());
		return vbox;
	}
	
	public void newGame() throws IOException {
		// Initial Set Up
		round = 1;
		wave = new WaveGenerator();
		model = new ViewModel(1080,1920);
		player = new Player();
		lsPath = new ArrayList<ImageView>();
		direction = new ArrayList<Integer>();
		// Set Up Other Player Area
		HBox top = createTop();
		
		// Set Up Primary Player
		HBox bottom = createBottom();
		// Set Up Market
		VBox market = createMarket();
		grid = createGrid();
		animationGrid = createClearGrid();
		attackGrid = createClickThrough();

		// Set Up Menu Bar
		MenuBar menu = createMenuBar();
		
		StackPane stack = new StackPane();
		stack.getChildren().add(grid);
		stack.getChildren().add(animationGrid);
		stack.getChildren().add(attackGrid);
		
		BorderPane root = new BorderPane();
		BorderPane pane = new BorderPane();
		root.setCenter(pane);
		root.setLeft(market);
		root.setTop(menu);
		pane.setCenter(stack);
		pane.setTop(top);
		pane.setBottom(bottom);

		stage.setScene(new Scene(root, model.getWidth(), model.getHeight()));
		stage.getScene().getStylesheets().add(getClass().getResource("mainView.css").toExternalForm());
		stage.setResizable(false);
		stage.setFullScreen(true);
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

		stage.sizeToScene();
		//loadMusic();
		stage.show();
	}
	
	private Pane createClickThrough() {
		Pane box = new Pane();
		box.setPrefHeight(model.getEffectiveBoardHeight());
		box.setPrefWidth(model.getEffectiveWidth());
		box.setMouseTransparent(true);
		box.setPickOnBounds(false);
		return box;
	}
	
	private GridPane createClearGrid() throws FileNotFoundException {
		GridPane grid = new GridPane();
		Viewable[][][] board = controller.getBoard().getBoard();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				ImageView view = new ImageView();
				view.setImage(new Image(new FileInputStream("./resources/images/Grass.png")));
				view.setFitHeight(SIZE_IMAGE+2);
				view.setFitWidth(SIZE_IMAGE+2);
				view.setOpacity(0);
				grid.add(view, i, j);
			}
		}
		grid.setMouseTransparent(true);
		grid.setPickOnBounds(false);
		return grid;
	}
	
	public GridPane createGrid() throws FileNotFoundException {
		GridPane grid = new GridPane();
		Viewable[][][] board = controller.getBoard().getBoard();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				HBox box = new HBox();
				box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1,1,1,1), Insets.EMPTY)));
				ImageView view = createGridResource(board[i][j][0], i, j);
				box.getChildren().add(view);
				grid.add(box, i, j);
			}
		}
		return grid;	
	}
	
	private ImageView createGridResource(Viewable obj, int row, int col) throws FileNotFoundException {
		ImageView x = ImageResourceLoadingHandler.getResource(obj);
		x.setFitHeight(SIZE_IMAGE);
		x.setFitWidth(SIZE_IMAGE);
		x.setOnMouseClicked(new GameObjectClickedHandler(obj, row, col, player, controller));
		return x;
	}

	public void update() {
		Thread thread = new Thread(()-> {
			List<Minion> currentWave = wave.generateRandom(round); 
			List<ImageView> minions = new ArrayList<ImageView>();
			for(Minion m: currentWave) {
				try {
					ImageView view = ImageResourceLoadingHandler.getResource(m);
					view.setFitHeight(SIZE_IMAGE+2);
					view.setFitWidth(SIZE_IMAGE+2);
					System.out.println(view);
					minions.add(view);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(Node n: grid.getChildren()) {
				if(GridPane.getColumnIndex(n)==currentXVal&&GridPane.getRowIndex(n)==currentYVal) {
					int finY = currentYVal;
					Platform.runLater(()->{
						for(int i =0;i<minions.size();i++) {
							animationGrid.add(minions.get(i), round-1, finY);
						}
					});
				}
			}
			for(int i =0;i<currentWave.size();i++) {
				try {
					move(i, currentWave.get(i), minions, currentWave);
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		generatePath(thread);
	}
	
	private void move(int index, Minion minion, List<ImageView> minions, List<Minion> minionsL) throws FileNotFoundException {
		if(minion.isDead()) {
			Platform.runLater(()->{
				animationGrid.getChildren().remove(minions.get(index));
			});
			player.increaseGold(minion.getReward());
			return;
		}
		int x = 0;
		int y = currentYVal;
		for(int k =0;k<minion.getStep();k++) {
			int dir = direction.get(k);
			if(dir==4||dir==2) {
				x+=dir==4?1:-1;
			}else {
				y+=dir==1?-1:1;
			}
		}
		checkTowers(minion, x, y);
		if(minion.isDead()) {
			Platform.runLater(()->{
				animationGrid.getChildren().remove(minions.get(index));
			});
			player.increaseGold(2);
			return;
		}
		int xFin = x;
		int yFin = y;
		Timeline t = new Timeline(new KeyFrame(Duration.millis(MINION_MAX_SPEED/minion.getSpeed()), (e)-> {
			if(minion.getStep()>=direction.size()-1) {
				minion.takeDamage(minion.getHealth());
				animationGrid.getChildren().remove(minions.get(index));
			}else {
				minion.incrementStep();
				animationGrid.getChildren().remove(minions.get(index));
				animationGrid.add(minions.get(index), xFin, yFin);
				try {
					move(index, minion, minions, minionsL);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}));
		t.play();
	}
	
	private void checkTowers(Minion minion, int x, int y) throws FileNotFoundException {
		Viewable[][][] map = controller.getBoard().getBoard();
		for(int i =0;i<map.length;i++) {
			for(int j =0;j<map[i].length;j++) {
				if(map[i][j][0]!=null&&map[i][j][0] instanceof Tower) {
					Tower t = (Tower)map[i][j][0];
					int range = t.getRange();
					if(range+i>=x&&-range+i<=x) {
						if(range+j>=y&&j-range<=y) {
							if(!t.canAttack()) {
								continue;
							}
							ImageView view = new ImageView();
							view.setImage(new Image(new FileInputStream("./resources/images/tst.jpeg")));
							view.setFitHeight(10);
							view.setFitWidth(10);
							Platform.runLater(()->{
								attackGrid.getChildren().add(view);
							});
							view.setX(i*SIZE_IMAGE+SIZE_IMAGE/2);
							view.setY(j*SIZE_IMAGE+SIZE_IMAGE/2);
							TranslateTransition tt = new TranslateTransition(Duration.millis((TOWER_MAX_ATTACK_SPEED/t.getAttackSpeed())*10), view);
							tt.setByX((x-i)*SIZE_IMAGE);
							tt.setByY((y-j)*SIZE_IMAGE);
							tt.play();
							t.startCooldown();
							System.out.println("On cooldown");
							tt.setOnFinished((e)->{
								minion.takeDamage(t.getAttack());
								attackGrid.getChildren().remove(view);
								t.endCooldown();
								System.out.println("Cooled down...");
							});
							return;
						}
					}
				}
			}
		}
	}
	
	private Node findNode(int col, int row) {
		for(Node n: grid.getChildren()) {
			if(GridPane.getColumnIndex(n)==col&&GridPane.getRowIndex(n)==row) {
				return ((HBox)n).getChildren().get(0);
			}
		}
		return null;
	}
	
	public void generatePath() {
		Viewable[][][] map = controller.getBoard().getBoard();
		int x = 0;
		int y = 0;
		for (int i = 0; i < map[0].length; i++) {
			if (map[0][i][0] instanceof Path) {
				lsPath.add((ImageView)findNode(0,i));
				y = i;
			}
		}
		while (true) {
			int topy = y - 1;
			int boty = y + 1;
			int leftx = x - 1;
			int rightx = x + 1;
			if (leftx >= 0) {
				if (map[leftx][y][0] instanceof Path&&!lsPath.contains(findNode(y, leftx))) {
					lsPath.add((ImageView)findNode(leftx, y));
					direction.add(1);
					x = leftx;
					continue;
				}
			}
		}
	}
					
	public void generatePath(Thread callback) {
		Thread thread = new Thread(()-> {
			Viewable[][][] map = controller.getBoard().getBoard();
			int x = 0;
			int y = 0;
			for (int i = 0; i < map[0].length; i++) {
				if (map[0][i][0] instanceof Path) {
					lsPath.add((ImageView)findNode(0,i));
					y = i;
					currentYVal = i;
				}
			}
			while (true) {
				int topy = y - 1;
				int boty = y + 1;
				int leftx = x - 1;
				int rightx = x + 1;
				if (leftx >= 0) {
					if (map[leftx][y][0] instanceof Path&&!lsPath.contains(findNode(leftx, y))) {
						lsPath.add((ImageView)findNode(leftx, y));
						direction.add(2);
						x = leftx;
						continue;
					}
				}
				if (topy >= 0) {
					if (map[x][topy][0] instanceof Path&&!lsPath.contains(findNode(x, topy))) {
						lsPath.add((ImageView)findNode(x, topy));
						direction.add(1);
						y = topy;
						continue;
					}
				}
				if (rightx < map.length) {
					if (map[rightx][y][0] instanceof Path&&!lsPath.contains(findNode(rightx, y))) {
						lsPath.add((ImageView)findNode(rightx, y));
						direction.add(4);
						x = rightx;
						continue;
					}
				}
				if (boty < map[0].length) {
					if (map[x][boty][0] instanceof Path && !lsPath.contains(findNode(x, boty))) {
						lsPath.add((ImageView)findNode(x, boty));
						direction.add(3);
						y = boty;
						continue;
					}
				}
				if (x == map.length-1) {
					break;
				}
				
			}
			callback.start();

		});
		thread.start();
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
		hp2.setTextFill(Color.WHITE);
		mp2.setTextFill(Color.WHITE);
		Text health = new Text();
		health.setFill(Color.WHITE);
		health.setText(player.getHealth()+"");
		Text gold = new Text();
		gold.setFill(Color.WHITE);
		gold.setText(player.getGold()+"");
		player.getViewableHealth().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				health.setText(arg2.toString());
			}
			
		});
		
		player.getViewableGold().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stubs
				gold.setText(arg2.toString());
			}
			
		});
		stat2.getChildren().addAll(hp2, health, mp2, gold);
		
		ListView<ImageView> pane = new ListView<ImageView>();
		pane.setItems(player.getHand());
		pane.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		pane.setOrientation(Orientation.HORIZONTAL);
		pane.setOnMouseClicked((e)->{
			pane.getSelectionModel().getSelectedItem().getOnMouseClicked().handle(e);
		});
		pane.setPrefWidth(1000);
		pane.setBackground(Background.EMPTY);
		
		bottom.getChildren().add(stat2);
		bottom.getChildren().add(pane);
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
		hp1.setTextFill(Color.WHITE);
		mp1.setTextFill(Color.WHITE);
		Text health = new Text();
		health.setFill(Color.WHITE);
		health.setText(player.getHealth()+"");
		Text gold = new Text();
		gold.setFill(Color.WHITE);
		gold.setText(player.getGold()+"");
		player.getViewableHealth().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				health.setText(arg2.toString());
			}
			
		});
		
		player.getViewableGold().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stubs
				gold.setText(arg2.toString());
			}
			
		});
		stat1.getChildren().addAll(hp1, health, mp1, gold);		
		
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
		
		ListView<ImageView> view = new ListView<ImageView>();
		Market m = controller.getMarket();
		view.setItems(m.getForSale());
		view.setPrefHeight(model.getHeight());
		market.getChildren().add(view);
		
		view.setOnMouseClicked((e)->{
			view.getSelectionModel().getSelectedItem().getOnMouseClicked().handle(e);
		});
		
		int prefWidth = 350;
		model.addSubWidth(prefWidth);
		market.setPrefWidth(prefWidth);
		input.close();
		return market;
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
		newGame.setOnAction((e) -> {
			try {
				newGame();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		MenuItem mapEditor = new MenuItem();
		mapEditor.setText("Open Map Editor");
		mapEditor.setOnAction(new MapEditorHandler());
		
		MenuItem testUpdate = new MenuItem();
		testUpdate.setText("Test Update");
		testUpdate.setOnAction((e)->{
			update();
			System.out.println("Updating");
		});
		
		MenuItem exit = new MenuItem();
		exit.setText("Exit");
		exit.setOnAction(new ExitHandler());
		
		file.getItems().addAll(newGame, mapEditor, testUpdate, exit);
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
		Viewable[][][] board = controller.getBoard().getBoard();
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
				node = createGridResource(obj, row, col);
				toRemove = n;
			}
		}
		if(node == null || toRemove == null) {
			return;
		}

		grid.getChildren().remove(toRemove);
		grid.add(node, col, row);
	}
}


