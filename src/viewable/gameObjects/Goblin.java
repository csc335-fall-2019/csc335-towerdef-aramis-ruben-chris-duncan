package viewable.gameObjects;

public class Goblin extends Minion {

	private static final int defaultHealth = 5;
	private static final int defaultDamage = 1;
	private static final int defaultSpeed = 1;
	private static final int defaultReward = 2;

	public Goblin(Integer health, Integer damage, Integer speed, Integer reward) {
		super(defaultHealth, defaultDamage, defaultSpeed, defaultReward);
	}

	@Override
	public String getResource() {
		// TODO Auto-generated method stub
		return null;
	}
}
