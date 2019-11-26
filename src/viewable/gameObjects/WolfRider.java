package viewable.gameObjects;

public class WolfRider extends Minion {
	
	private static final int defaultHealth = 10;
	private static final int defaultDamage = 2;
	private static final int defaultSpeed = 2;
	private static final int defaultReward = 5;

	public WolfRider(Integer health, Integer damage, Integer speed, Integer reward) {
		super(defaultHealth, defaultDamage, defaultSpeed, defaultReward);
	}

}
