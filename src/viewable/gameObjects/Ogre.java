package viewable.gameObjects;

public class Ogre extends Minion {
	
	private static final int defaultHealth = 15;
	private static final int defaultDamage = 5;
	private static final int defaultSpeed = 1;
	private static final int defaultReward = 10;

	public Ogre(Integer health, Integer damage, Integer speed, Integer reward) {
		super(defaultHealth, defaultDamage, defaultSpeed, defaultReward);
	}

	@Override
	public String getResource() {
		// TODO Auto-generated method stub
		return null;
	}

}
