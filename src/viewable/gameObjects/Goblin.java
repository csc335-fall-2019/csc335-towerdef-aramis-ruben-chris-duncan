package viewable.gameObjects;

import java.io.File;
import java.io.IOException;

public class Goblin extends Minion {

	private static final int defaultHealth = 5;
	private static final int defaultDamage = 1;
	private static final int defaultSpeed = 1;
	private static final int defaultReward = 2;

	public Goblin() {
		super(defaultHealth, defaultDamage, defaultSpeed, defaultReward);
	}

	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/Goblin/Goblin_01.png")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}
}
