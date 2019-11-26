/** @author Chris Loper
 * 
 * Contains card id and card name for Archer Towers, also
 * contains an upgrade class for Archer Towers
 * 
 */
package viewable.cards.towers;

import java.io.File;
import java.io.IOException;

import viewable.cards.Card;
import viewable.gameObjects.ArcherTower;
import viewable.gameObjects.Player;

public class ArcherTowerCard extends Card {
	private static final int cardId 		= 0;
	private static final String cardName 	= "Archer Tower";
	private static final int cardCost       = 1;
	
	public ArcherTowerCard() {
		super(cardName, cardId, cardCost);
	}
	
	public void Upgrade(ArcherTower t) {
		t.setAttack(t.getAttack() + 1);
		t.setRange(t.getRange() + 1);
		t.setAttackSpeed(t.getAttackSpeed() + .1);
	}

	@Override
	public void Ability(Player p) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public String getResource() {
		
	}
}
