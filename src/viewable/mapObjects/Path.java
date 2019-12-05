package viewable.mapObjects;

import java.io.File;
import java.io.IOException;

import viewable.Viewable;

public class Path extends Viewable{

	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/path.png")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

}
