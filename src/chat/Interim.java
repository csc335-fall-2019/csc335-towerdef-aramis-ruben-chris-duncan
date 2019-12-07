package chat;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;


public class Interim extends Application{
	@Override
	public void start(Stage primaryStage) {
		ChatView view = new ChatView();
		try {
			primaryStage = view.create(7000);
			ChatView view2 = new ChatView();
			Stage stage = view2.create(6000);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		primaryStage.show();
	}
}
