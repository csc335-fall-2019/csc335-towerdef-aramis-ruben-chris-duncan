package network;

public class StatIncreaseMessage extends TowerDefenseMoveMessage{
	private int healthIncrease;
	private int goldIncrease;
	
	public StatIncreaseMessage(int h, int g) {
		this.healthIncrease = h;
		this.goldIncrease = g;
	}
	
	public int getHealth() {
		return healthIncrease;
	}
	
	public int getGold() {
		return goldIncrease;
	}
}
