package network;

public class OtherStatIncreaseMessage extends TowerDefenseMoveMessage{
	private int healthIncrease;
	private int goldIncrease;
	
	public OtherStatIncreaseMessage(int h, int g) {
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
