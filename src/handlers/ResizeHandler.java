package handlers;

import game.ViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class ResizeHandler implements ChangeListener<Number>{
		private ViewModel model;
		private Stage primaryStage;
		private Pane root;
		
		public ResizeHandler(ViewModel model, Stage primaryStage, Pane root) {
			this.model = model;
			this.primaryStage = primaryStage;
			this.root = root;
		}
	
		@Override
		public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
			double newW = primaryStage.getScene().getWidth();
			double newH= primaryStage.getScene().getHeight();
			primaryStage.getScene().getRoot().getTransforms().add(new Scale(newW/model.getWidth(), newH/model.getHeight()));
			model.setWidth((int)newW);
			model.setHeight((int)newH);
		}
}
