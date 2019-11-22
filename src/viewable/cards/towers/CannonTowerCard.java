/** @author Chris Loper
 * 
 * Cannon Tower Card
 * Contains default card ID and card name for Cannon Towers, also
 * contains an upgrade class to upgrade Cannon Towers
 * 
 */
package viewable.cards.towers;

import viewable.cards.Card;
import viewable.gameObjects.CannonTower;

public class CannonTowerCard extends Card {
	private static final int cardId 		= 1;
	private static final String cardName 	= "Cannon Tower";
	
	public CannonTowerCard() {
		super(cardName, cardId);
	}
	
	public void Upgrade(CannonTower c) {
		c.setAttack(c.getAttack() + 1);
		c.setRange(c.getRange() + 1);
		c.setAttackSpeed(c.getAttackSpeed() + .1);
	}
}
