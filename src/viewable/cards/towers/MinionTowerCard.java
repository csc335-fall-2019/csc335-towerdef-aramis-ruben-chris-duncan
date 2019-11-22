/** @author Chris Loper
 * 
 * Contains card id and card name for Minion Towers, also
 * contains an upgrade class for Minion Towers
 * 
 */
package viewable.cards.towers;

import viewable.cards.Card;
import viewable.gameObjects.MinionTower;
import viewable.gameObjects.Player;

public class MinionTowerCard extends Card {
	private static final int cardId 		= 5;
	private static final String cardName 	= "Minion Tower";
	
	public MinionTowerCard() {
		super(cardName, cardId);
	}
	
	public void Upgrade(MinionTower m) {
		m.setAttack(m.getAttack() + 1);
		m.setRange(m.getRange() + 0);
		m.setAttackSpeed(m.getAttackSpeed() + .1);
	}
	
	@Override
	public void Ability(Player p) {
		// TODO Auto-generated method stub
	}
}
