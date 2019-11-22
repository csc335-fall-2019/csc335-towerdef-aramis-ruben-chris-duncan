/** @author Chris Loper
 * 
 * Contains card id and card name for Freeze Towers, also
 * contains an upgrade class for Freeze Towers
 * 
 */
package viewable.cards.towers;

import viewable.cards.Card;
import viewable.gameObjects.FreezeTower;
import viewable.gameObjects.Player;

public class FreezeTowerCard extends Card {
	private static final int cardId 		= 3;
	private static final String cardName 	= "Freeze Tower";
	
	public FreezeTowerCard() {
		super(cardName, cardId);
	}
	
	public void Upgrade(FreezeTower f) {
		f.setAttack(f.getAttack() + 0);
		f.setRange(f.getRange() + 1);
		f.setAttackSpeed(f.getAttackSpeed() + .1);
	}
	
	@Override
	public void Ability(Player p) {
		// TODO Auto-generated method stub
	}
}
