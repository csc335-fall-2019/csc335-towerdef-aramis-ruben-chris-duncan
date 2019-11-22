package viewable.gameObjects;

import java.io.File;
import java.io.IOException;

public class BasicTower extends Tower{
	public BasicTower() {
	}
	
	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/tst.jpeg")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}
}
