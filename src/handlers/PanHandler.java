package handlers;

import game.ViewModel;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PanHandler implements EventHandler<MouseEvent>{
	private static final int TOLERANCE = 50;
	private ViewModel model;
	private Stage stage;
	public PanHandler(ViewModel m, Stage s) {
		model = m;
		stage = s;
	}
	
	@Override
	public void handle(MouseEvent e) {
		if((!(model.getCurrentCol()>=15)&&(e.getX()>(stage.getWidth()-TOLERANCE)))||(e.getX()<TOLERANCE&&!(model.getCurrentCol()==0))) {
			
		}
		if((!(model.getCurrentRow()>=15)&&(e.getY()>(model.getEffectiveBoardHeight()-TOLERANCE)))||(e.getY()<TOLERANCE&&!(model.getCurrentRow()==0))) {
			
		}
	}

}
