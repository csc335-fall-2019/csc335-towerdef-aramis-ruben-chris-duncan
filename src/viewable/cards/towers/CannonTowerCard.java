/** @author Chris Loper
 * 
 * Cannon Tower Card
 * Contains default card ID and card name for Cannon Towers, also
 * contains an upgrade class to upgrade Cannon Towers
 * 
 */
package viewable.cards.towers;

import java.io.File;
import java.io.IOException;

import viewable.cards.Card;
import viewable.gameObjects.CannonTower;
import viewable.gameObjects.Player;

public class CannonTowerCard extends Card {
	private static final int cardId 		= 1;
	private static final String cardName 	= "Cannon Tower";
	private static final int cardCost       = 1;
	
	public CannonTowerCard() {
		super(cardName, cardId, cardCost);
	}
	
	public void Upgrade(CannonTower c) {
		c.setAttack(c.getAttack() + 1);
		c.setRange(c.getRange() + 1);
		c.setAttackSpeed(c.getAttackSpeed() + .1);
	}

	@Override
	public void Ability(Player p) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/cannonTowerCard.png")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}
}
