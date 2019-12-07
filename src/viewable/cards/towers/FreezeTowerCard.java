/** @author Chris Loper
 * 
 * Contains card id and card name for Freeze Towers, also
 * contains an upgrade class for Freeze Towers
 * 
 */
package viewable.cards.towers;

import java.io.File;
import java.io.IOException;

import viewable.cards.Card;
import viewable.gameObjects.FreezeTower;
import viewable.gameObjects.Player;
import viewable.gameObjects.Tower;

public class FreezeTowerCard extends TowerCard {
	private static final int cardId 		= 3;
	private static final String cardName 	= "Freeze Tower";
	private static final int cardCost       = 25;
	
	public FreezeTowerCard() {
		super(cardName, cardId, cardCost);
	}
	
	public void Upgrade(FreezeTower f) {
		f.setAttack(f.getAttack() + 0);
		f.setRange(f.getRange() + 1);
		f.setAttackSpeed(f.getAttackSpeed() + .1);
	}
	
	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/freezeTowerCard.png")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

	@Override
	public Class<? extends Tower> getTower() {
		// TODO Auto-generated method stub
		return FreezeTower.class;
	}
}
