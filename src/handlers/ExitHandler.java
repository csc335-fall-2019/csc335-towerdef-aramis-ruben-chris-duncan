package handlers;
/**
 * ExitHandler.java
 * 
 * Handles the exit of the program
 */
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ExitHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		Platform.exit();
	}

}
