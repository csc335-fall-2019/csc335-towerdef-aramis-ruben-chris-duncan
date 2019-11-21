import handlers.ExitHandler;
import handlers.NewGameHandler;
import handlers.SoundHandler;
import handlers.VideoHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import towers.Tower;

public class TowerDefenseView extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {
		TowerDefenseController controller = new TowerDefenseController();
		ViewModel model = new ViewModel(500,500);

		BorderPane root = new BorderPane();
		
		root.setTop(createMenuBar(model));
		root.setCenter(createBoard(controller, model));
		primaryStage.setScene(new Scene(root, model.getWidth(), model.getHeight()));
		primaryStage.show();
	}
	
	private GridPane createBoard(TowerDefenseController controller, ViewModel model) {
		GridPane pane = new GridPane();
		Tower[][] towers = controller.getBoard();
		for(int i =0;i<towers.length;i++) {
			for(int j =0;j<towers[i].length;j++) {
				System.out.println(i+" "+j+" "+model.getWidth()/10+" "+model.getEffectiveBoardHeight()/10);
				pane.add(new Rectangle(model.getWidth()/10,model.getEffectiveBoardHeight()/10), j, i);
			}
		}
		
		return pane;
	}
	
	private MenuBar createMenuBar(ViewModel model) {
		// Create the menu bar.
		MenuBar bar = new MenuBar();
		
		bar.getMenus().add(createFileMenu());
		bar.getMenus().add(createOptionMenu());
		int menuHeight = 30;
		bar.setPrefHeight(menuHeight);
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
