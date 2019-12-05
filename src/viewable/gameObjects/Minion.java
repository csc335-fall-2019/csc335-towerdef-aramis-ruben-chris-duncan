package viewable.gameObjects;

import viewable.Viewable;

public abstract class Minion extends Viewable {
	
	private Integer health;
	private Integer damage;
	private Integer speed;
	private Integer reward;
	private Integer currentHealth;
	private Integer currentStep;
	
	public Minion(Integer health, Integer damage, Integer speed, Integer reward) {
		this.health = health;
		currentHealth = health;
		currentStep = 0;
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
	
	public void incrementStep() {
		currentStep++;
	}
	
	public int getStep() {
		return currentStep;
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
	
	public void takeDamage(int damage) {
		currentHealth-=damage;
	}
	
	public int getCurrentHealth() {
		return currentHealth;
	}
	
	public boolean isDead() {
		return currentHealth<=0;
	}
	
}
