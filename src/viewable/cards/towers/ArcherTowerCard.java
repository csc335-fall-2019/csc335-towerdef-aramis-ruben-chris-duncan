package viewable.cards.towers;

import viewable.cards.Card;
import viewable.towers.ArcherTower;

public class ArcherTowerCard extends Card {
	private static final int cardId 		= 0;
	private static final String cardName 	= "Archer Tower";
	
	public ArcherTowerCard() {
		super(cardName, cardId);
	}
	
	public void Upgrade(ArcherTower t) {
		t.setAttack(t.getAttack() + 1);
		t.setRange(t.getRange() + 1);
		t.setAttackSpeed(t.getAttackSpeed() + .1);
	}
	
}
