package viewable.gameObjects;

public class Charger extends Minion {
	
	private static final int defaultHealth = 15;
	private static final int defaultDamage = 5;
	private static final int defaultSpeed = 2;
	private static final int defaultReward = 10;

	public Charger(Integer health, Integer damage, Integer speed, Integer reward) {
		super(defaultHealth, defaultDamage, defaultSpeed, defaultReward);
	}

}
