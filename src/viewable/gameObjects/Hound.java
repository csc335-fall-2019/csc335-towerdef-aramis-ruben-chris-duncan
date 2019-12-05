package viewable.gameObjects;

import java.io.File;
import java.io.IOException;

public class Hound extends Minion {
	
	private static final int defaultHealth = 5;
	private static final int defaultDamage = 1;
	private static final int defaultSpeed = 2;
	private static final int defaultReward = 2;

	public Hound() {
		super(defaultHealth, defaultDamage, defaultSpeed, defaultReward);
	}

	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/MonsterSprites/Hound.png")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

}
