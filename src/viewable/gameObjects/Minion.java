package viewable.gameObjects;

import viewable.Viewable;

public abstract class Minion extends Viewable {
	
	private Integer health;
	private Integer damage;
	private Integer speed;
	private Integer reward;
	
	public Minion(Integer health, Integer damage, Integer speed, Integer reward) {
		this.health = health;
		this.damage = damage;
		this.speed = speed;
		this.reward = reward;
	}
	
	public void setHealth(Integer newHealth) {
		this.health = newHealth;
	}
	
	public void setDamage(Integer newDamage) {
		this.damage = newDamage;
	}
	
	public void setSpeed(Integer newSpeed) {
		this.speed = newSpeed;
	}
	
	public Integer getHealth() {
		return this.health;
	}
	
	public Integer getDamage() {
		return this.damage;
	}
	
	public Integer getSpeed() {
		return this.speed;
	}
	
	public Integer getReward() {
		return this.reward;
	}
	
}
