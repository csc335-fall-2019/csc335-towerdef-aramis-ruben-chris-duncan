package game;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

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
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import viewable.Viewable;
import viewable.gameObjects.WaveGenerator;
import viewable.mapObjects.Path;
import viewable.gameObjects.Map;
import viewable.gameObjects.Market;
import viewable.gameObjects.Minion;
import viewable.gameObjects.Player;
import viewable.gameObjects.Tower;

public class TowerDefenseView extends Application implements Observer{
	public static Stage MESSAGE_RECEIVED;
	private static final int SIZE_IMAGE = 47;
	private static final int CARD_WIDTH = 128;
	private static final int CARD_HEIGHT = 196;
	private static final int MINION_MAX_SPEED = 100;
	private static final int TOWER_MAX_ATTACK_SPEED = 3;
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
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		System.out.println(stage);
		controller = new TowerDefenseController(this);
		Runtime.getRuntime().addShutdownHook(new Thread(()->{
			controller.setRunning(false);
			System.out.println("Exiting...");
		}));
		currentYVal = 0;
		mainMenu();
	}
	
	private void mainMenu() throws IOException {
		VBox vbox = new VBox(25);
		vbox.setPadding(new Insets(20));
		FileInputStream in = new FileInputStream("./resources/images/splashScreen.gif");
		Image logo = new Image(in);
		ImageView logoView = new ImageView(logo);
		in.close();
		HBox buttons = new HBox(15);
		
		Button hostGame = new Button("Host a Game");
		hostGame.setOnAction((e)->{
			FileChooser fileChooser = new FileChooser();
			
			File initDir = new File("./saves");
			initDir.mkdir();
			
			fileChooser.setInitialDirectory(initDir);
			
			fileChooser.setTitle("Open Resource File");
			File path = fileChooser.showOpenDialog(stage);
			if (path != null) {
				try {
					controller.getBoard().load(path.getCanonicalPath());
					controller.startServer();
					hostGame.setDisable(true);
					newGame();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					fileChooser.showOpenDialog(stage);
				}
				
			}
		});
		Button newGame = new Button("Connect");
		Button mapEditor = new Button("Map Editor");
		Button exit = new Button("Exit");
		buttons.getChildren().addAll(newGame, hostGame, mapEditor, exit);
		

		buttons.setAlignment(Pos.CENTER);
		vbox.getChildren().add(logoView);
		vbox.getChildren().add(buttons);
		
		newGame.setOnAction((e) -> {
			connectOrHost();
		});
		
		mapEditor.setOnAction(new MapEditorHandler());
		exit.setOnAction(new ExitHandler());
		Scene scene = new Scene(vbox);
		stage.setTitle("Power Tower");
		stage.setScene(scene);
		stage.show();
	}
	
	private void connectOrHost() {
		GridPane pane = new GridPane();
		BorderPane main = new BorderPane();
		
		Button timer = new Button("Scan for Games");
		timer.setOnAction((e)->{
			try {
				controller.scanPorts();
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		});
		
		ListView view = new ListView();
		view.setItems(controller.getPossibleConnections());
		view.setCellFactory(new Callback() {
			@Override
			public Object call(Object arg0) {
				// TODO Create the server view.
	            return new PossibleConnectionCell();
			}
		});
		view.setOnMouseClicked((e)->{
			if(!e.getButton().equals(MouseButton.PRIMARY)) {
				e.consume();
				return;
			}else if(e.getClickCount()<2) {
				e.consume();
				return;
			}
			System.out.println("Connecting...");
			InetSocketAddress address = (InetSocketAddress)view.getSelectionModel().getSelectedItem();
			if(address==null) {
				e.consume();
				return;
			}
			controller.startClient(address.getHostString(), address.getPort());
		});

		TextField host = new TextField();
		Label hostText = new Label("Host");
		
		TextField port = new TextField();
		Label portText = new Label("Port");
		
		Button client = new Button("Connect to Host");
		client.setOnAction((e)->{
			if(host.getText().length()==0||port.getText().length()==0) {
				Stage error = new Stage();
				error.showAndWait();
				e.consume();
			}
			try {
				Integer.parseInt(port.getText());
			}catch(Exception ex) {
				Stage error = new Stage();
				error.showAndWait();
				e.consume();
			}
			controller.startClient(host.getText(), Integer.parseInt(port.getText()));
		});

		pane.add(host, 0, 1);
		pane.add(hostText, 0, 0);
		pane.add(port, 1, 1);
		pane.add(portText, 1, 0);
		pane.add(client, 0, 2);
		pane.add(timer, 3, 2);
		
		main.setCenter(view);
		main.setBottom(pane);
		stage.setScene(new Scene(main, 1920, 1080));
		stage.setFullScreen(true);
		stage.show();
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
			round++;
			List<ImageView> minions = new ArrayList<ImageView>();
			for(Minion m: currentWave) {
				try {
					ImageView view = ImageResourceLoadingHandler.getResource(m);
					view.setFitHeight(SIZE_IMAGE+2);
					view.setFitWidth(SIZE_IMAGE+2);
					minions.add(view);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(Node n: grid.getChildren()) {
				if(GridPane.getColumnIndex(n)==0&&GridPane.getRowIndex(n)==currentYVal) {
					int finY = currentYVal;
					Platform.runLater(()->{
						for(int i =0;i<minions.size();i++) {
							animationGrid.add(minions.get(i), 0, finY);
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
	
	private void move(int index, Minion minion, List<ImageView> minions, List<Minion> minionsL) {
		if(minion.isDead()) {
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
		int xFin = x;
		int yFin = y;
		Timeline t = new Timeline(new KeyFrame(Duration.millis(MINION_MAX_SPEED/minion.getSpeed()), (e)-> {
			try {
				checkTowers(minion, xFin, yFin);
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if(minion.isDead()) {
				Platform.runLater(()->{
					animationGrid.getChildren().remove(minions.get(index));
				});
				player.increaseGold(minion.getReward());
				return;
			}
			if(minion.getStep()>=direction.size()-1) {
				controller.damageOther(minion.getDamage());
				minion.takeDamage(minion.getHealth());
				animationGrid.getChildren().remove(minions.get(index));
			}else {
				minion.incrementStep();
				animationGrid.getChildren().remove(minions.get(index));
				animationGrid.add(minions.get(index), xFin, yFin);
				move(index, minion, minions, minionsL);
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
							t.startCooldown();
							tt.setOnFinished((e)->{
								minion.takeDamage(t.getAttack());
								attackGrid.getChildren().remove(view);
								t.endCooldown();
							});
							tt.play();
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
	
	public void generatePath(Thread callback) {
		Thread thread = new Thread(()-> {
			System.out.println("Started.");
			if(lsPath.size()>0&&direction.size()>0) {
				callback.start();
				return;
			}
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
		if(e instanceof Map) {
				Platform.runLater(()->{
					try {
						newGame();
					}catch(Exception ex) {
						ex.printStackTrace();
					}
				});
		}else if(e instanceof String) {
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
	private class PossibleConnectionCell extends ListCell{
		private InetSocketAddress address;
		public PossibleConnectionCell() {
		}
		
		@Override
		protected void updateItem(Object update, boolean empty) {
			super.updateItem(update, empty);
			address = (InetSocketAddress)update;
			if(update!=null) {
				HBox box = new HBox();
				Label addressLabel = new Label(address.getHostString());
				box.getChildren().add(addressLabel);
				setGraphic(box);
			}
		}
		
		public InetSocketAddress getAddress() {
			return address;
		}
	}
}


