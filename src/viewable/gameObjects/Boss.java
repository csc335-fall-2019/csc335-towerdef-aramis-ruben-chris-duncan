package viewable.gameObjects;

public class Boss extends Minion {
	
	private static final int defaultHealth = 100;
	private static final int defaultDamage = 10;
	private static final int defaultSpeed = 1;
	private static final int defaultReward = 100;

	public Boss(Integer health, Integer damage, Integer speed, Integer reward) {
		super(defaultHealth, defaultDamage, defaultSpeed, defaultReward);
	}

}
