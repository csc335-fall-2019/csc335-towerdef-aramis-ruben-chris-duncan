package handlers;

import java.io.FileNotFoundException;

import game.MapEditor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class MapEditorHandler implements EventHandler<ActionEvent>{
	@Override
	public void handle(ActionEvent e) {
		MapEditor mapEditor = new MapEditor();
		Stage stage;
		try {
			stage = mapEditor.create();
			stage.show();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
