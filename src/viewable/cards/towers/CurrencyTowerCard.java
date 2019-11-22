/** @author Chris Loper
 * 
 * Contains card id and card name for Currency Towers, also
 * contains an upgrade class for Currency Towers
 * 
 */
package viewable.cards.towers;

import viewable.cards.Card;
import viewable.towers.CurrencyTower;

public class CurrencyTowerCard extends Card {
	private static final int cardId 		= 4;
	private static final String cardName 	= "Currency Tower";
	
	public CurrencyTowerCard() {
		super(cardName, cardId);
	}
	
	public void Upgrade(CurrencyTower m) {
		m.setAttack(m.getAttack() + 1);
		m.setRange(m.getRange() + 0);
		m.setAttackSpeed(m.getAttackSpeed() + .1);
	}
}
