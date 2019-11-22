package viewable.gameObjects;
import viewable.Viewable;

public abstract class Tower extends Viewable{
	
	private Integer attack;
	private Integer range;
	private double attackSpeed;
	
	public Tower(Integer attack, Integer range, double attackSpeed) {
		this.attack 		= attack;
		this.range 			= range;
		this.attackSpeed 	= attackSpeed;
	}
	
	public void setAttack(Integer newAttack) {
		this.attack = newAttack;
	}
	
	public void setRange(Integer newRange) {
		this.range = newRange;
	}
	
	public void setAttackSpeed(double newAttackSpeed) {
		this.attackSpeed = newAttackSpeed;
	}
	
	public Integer getAttack() {
		return this.attack;
	}
	
	public Integer getRange() {
		return this.range;
	}
	
	public double getAttackSpeed() {
		return this.attackSpeed;
	}
}
