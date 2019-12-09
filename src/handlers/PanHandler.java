package handlers;
/**
 * PanHandler.java
 * 
 * Handles panning left and right on the game board
 * 
 * Usage instructions:
 * 
 * Construct PanHandler
 * PanHandler p = new PanHandler(view model, stage)
 */

import game.ViewModel;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PanHandler implements EventHandler<MouseEvent>{
	private static final int TOLERANCE = 50;
	private ViewModel model;
	private Stage stage;
	
	/**
	 * Purpose - constructor for the class
	 * @param m - a ViewModel oject
	 * @param s - a Stage object
	 */
	public PanHandler(ViewModel m, Stage s) {
		model = m;
		stage = s;
	}
	
	/**
	 *  Purpose - Handles mouse events that pan the screen left and right
	 *  
	 *  @param e - the MouseEvent being handled
	 */
	@Override
	public void handle(MouseEvent e) {
		if((!(model.getCurrentCol()>=15)&&(e.getX()>(stage.getWidth()-TOLERANCE)))||(e.getX()<TOLERANCE&&!(model.getCurrentCol()==0))) {
			
		}
		if((!(model.getCurrentRow()>=15)&&(e.getY()>(model.getEffectiveBoardHeight()-TOLERANCE)))||(e.getY()<TOLERANCE&&!(model.getCurrentRow()==0))) {
			
		}
	}

}
