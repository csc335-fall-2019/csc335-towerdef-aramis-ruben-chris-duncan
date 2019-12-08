/** @author Chris Loper
 * 
 * Super class for all Tower Game Objects
 * Contains all accessors and mutators for tower game objects
 * 
 */
package viewable.gameObjects;
import viewable.Viewable;

public abstract class Tower extends Viewable{
	private Integer attack;
	private Integer range;
	private double attackSpeed;
	private String name;
	private boolean onCooldown;
	private boolean upgraded;
	
	public Tower(Integer attack, Integer range, double attackSpeed, String name) {
		this.attack 		= attack;
		this.range 			= range;
		this.attackSpeed 	= attackSpeed;
		this.name			= name;
		upgraded = false;
	}
	/** 
	 * @purpose Mutator for attack
	 * @param newAttack
	 */
	public void setAttack(Integer newAttack) {
		this.attack = newAttack;
	}
	/**
	 * @purpose Mutator for range
	 * @param newRange
	 */
	public void setRange(Integer newRange) {
		this.range = newRange;
	}
	/**
	 * @purpose Mutator for attack speed
	 * @param newAttackSpeed
	 */
	public void setAttackSpeed(double newAttackSpeed) {
		this.attackSpeed = newAttackSpeed;
	}
	/**
	 * @purpose Accessor for attack
	 * @return this.attack
	 */
	public Integer getAttack() {
		return this.attack;
	}
	/**
	 * @purpose Accessor for range
	 * @return this.range
	 */
	public Integer getRange() {
		return this.range;
	}
	/**
	 * @purpose Accessor for attack speed
	 * @return this.attackSpeed
	 */
	public double getAttackSpeed() {
		return this.attackSpeed;
	}
	/**
	 * @purpose Accessor for name
	 * @return this.name
	 */
	public String getName() {
		return this.name;
	}
	
	public void startCooldown() {
		onCooldown = true;
	}
	
	public void endCooldown() {
		onCooldown = false;
	}
	
	public boolean canAttack() {
		return onCooldown == false;
	}
	
	public void setUpgraded(boolean val) {
		upgraded = val;
	}
	
	public boolean getUpgraded() {
		return upgraded;
	}
}
