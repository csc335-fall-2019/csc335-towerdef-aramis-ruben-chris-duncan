package network;

public class DamageOtherMessage extends TowerDefenseMoveMessage{
	private int amount;
	
	public DamageOtherMessage(int amount) {
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}
}
