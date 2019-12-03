/** @author Chris Loper
 * 
 * Contains card id and card name for Mage Towers, also
 * contains an upgrade class for Mage Towers
 * 
 */
package viewable.cards.towers;

import java.io.File;
import java.io.IOException;

import viewable.cards.Card;
import viewable.gameObjects.MageTower;
import viewable.gameObjects.Player;
import viewable.gameObjects.Tower;

public class MageTowerCard extends TowerCard {
	private static final int cardId 		= 2;
	private static final String cardName 	= "Mage Tower";
	private static final int cardCost       = 1;
	
	public MageTowerCard() {
		super(cardName, cardId, cardCost);
	}
	
	public void Upgrade(MageTower m) {
		m.setAttack(m.getAttack() + 1);
		m.setRange(m.getRange() + 1);
		m.setAttackSpeed(m.getAttackSpeed() + .1);
	}
	
	@Override
	public void Ability(Player p) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/mageTowerCard.png")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

	@Override
	public Class<? extends Tower> getTower() {
		// TODO Auto-generated method stub
		return MageTower.class;
	}
}
