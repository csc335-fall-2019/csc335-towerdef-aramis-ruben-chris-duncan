package viewable;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public abstract class Viewable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1121270826949137583L;
	
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
