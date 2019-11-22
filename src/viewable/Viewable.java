package viewable;

import java.io.File;
import java.io.IOException;

public abstract class Viewable {
	public String getResource;

	public String getResource() {
		// TODO Auto-generated method stub
		return "";
	}
	
	public static String getDefaultResource() {
		try {
			return (new File("./resources/images/test.jpg")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
}
