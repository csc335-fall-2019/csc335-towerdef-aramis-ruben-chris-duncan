package viewable.gameObjects;

public class Hound extends Minion {
	
	private static final int defaultHealth = 5;
	private static final int defaultDamage = 1;
	private static final int defaultSpeed = 2;

	public Hound(Integer health, Integer damage, Integer speed) {
		super(defaultHealth, defaultDamage, defaultSpeed);
	}

}
