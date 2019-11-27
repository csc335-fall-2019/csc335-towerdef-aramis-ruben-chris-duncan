package viewable.gameObjects;

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
		// TODO Auto-generated method stub
		return null;
	}

}
