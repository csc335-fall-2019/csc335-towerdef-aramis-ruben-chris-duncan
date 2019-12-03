package handlers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import viewable.Viewable;

public class ImageResourceLoadingHandler {
	public static ImageView getResource(Viewable obj) throws FileNotFoundException {
		ImageView view;
		if(obj == null) {
			view = new ImageView(new Image(new FileInputStream(Viewable.getDefaultResource())));
		}else {
			view = new ImageView(new Image(new FileInputStream(obj.getResource())));
		}
		
		return view;
	}
}
