package viewable.gameObjects;

public class Goblin extends Minion {

	private static final int defaultHealth = 5;
	private static final int defaultDamage = 1;
	private static final int defaultSpeed = 1;

	public Goblin(Integer health, Integer damage, Integer speed) {
		super(defaultHealth, defaultDamage, defaultSpeed);
	}
}
