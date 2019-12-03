package viewable.gameObjects;

import java.io.File;
import java.io.IOException;

public class WolfRider extends Minion {
	
	private static final int defaultHealth = 10;
	private static final int defaultDamage = 2;
	private static final int defaultSpeed = 2;
	private static final int defaultReward = 5;

	public WolfRider() {
		super(defaultHealth, defaultDamage, defaultSpeed, defaultReward);
	}

	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/tst.png")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

}
