package viewable.gameObjects;

import java.io.File;
import java.io.IOException;

public class Ogre extends Minion {
	
	private static final int defaultHealth = 15;
	private static final int defaultDamage = 5;
	private static final int defaultSpeed = 1;
	private static final int defaultReward = 10;

	public Ogre() {
		super(defaultHealth, defaultDamage, defaultSpeed, defaultReward);
	}

	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/Ogre/Ogre_01.png")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

}
