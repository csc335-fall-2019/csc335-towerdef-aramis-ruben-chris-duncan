package viewable.gameObjects;

import java.io.File;
import java.io.IOException;

public class GoblinKnight extends Minion {

	private static final int defaultHealth = 10;
	private static final int defaultDamage = 2;
	private static final int defaultSpeed = 1;
	private static final int defaultReward = 5;
	
	public GoblinKnight() {
		super(defaultHealth, defaultDamage, defaultSpeed, defaultReward);
	}

	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/GoblinKnight_01.png")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

}
