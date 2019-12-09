package network;

public class MinionDamageMessage extends TowerDefenseMoveMessage{
	private int amount;
	public MinionDamageMessage(int amount) {
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}
}
