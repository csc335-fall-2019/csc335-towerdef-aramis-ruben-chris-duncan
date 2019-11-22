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
			System.out.println(newW+" "+model.getWidth());
			System.out.println(newH+" "+model.getHeight());
			double scaleX = model.getWidth()<newW?newW/model.getWidth():model.getWidth()/newW;
			double scaleY = model.getHeight()<newH?newH/model.getHeight():model.getHeight()/newH;
			primaryStage.getScene().getRoot().getTransforms().add(new Scale(scaleX, scaleY));
			model.setWidth((int)newW);
			model.setHeight((int)newH);
		}
}
