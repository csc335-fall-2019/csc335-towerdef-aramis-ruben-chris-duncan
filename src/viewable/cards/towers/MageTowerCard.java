/** @author Chris Loper
 * 
 * Contains card id and card name for Mage Towers, also
 * contains an upgrade class for Mage Towers
 * 
 */
package viewable.cards.towers;

import viewable.cards.Card;
import viewable.gameObjects.MageTower;

public class MageTowerCard extends Card {
	private static final int cardId 		= 2;
	private static final String cardName 	= "Mage Tower";
	
	public MageTowerCard() {
		super(cardName, cardId);
	}
	
	public void Upgrade(MageTower m) {
		m.setAttack(m.getAttack() + 1);
		m.setRange(m.getRange() + 1);
		m.setAttackSpeed(m.getAttackSpeed() + .1);
	}
}
