import org.junit.jupiter.api.Test;

import viewable.cards.*;
import viewable.cards.towers.ArcherTowerCard;
import viewable.gameObjects.*;

class ViewableGameObjectsTests {

	@Test
	void ArcherTowerTest() {
		Tower t = new ArcherTower();
		t.getResource();
	}
	
	@Test
	void CannonTowerTest() {
		Tower t = new CannonTower();
		t.getResource();
	}
	
	@Test
	void CannonTower() {
		Tower t = new CannonTower();
		t.getResource();
	}
	
	@Test
	void CurrencyTowerTest() {
		Tower t = new CurrencyTower();
		t.getResource();
	}
	
	@Test
	void FreezeTowerTest() {
		Tower t = new FreezeTower();
		t.getResource();
	}
	
	@Test
	void MageTowerTest() {
		Tower t = new MageTower();
		t.getResource();
	}
	
	@Test
	void GoblinKnightTest() {
		Minion m = new GoblinKnight();
		m.getResource();
	}
	
	@Test
	void HoundTest() {
		Minion m = new Hound();
		m.getResource();
	}
	
	@Test
	void GoblinTest() {
		Minion m = new Goblin();
		m.getResource();
	}
	
	@Test
	void BossTest() {
		Minion m = new Boss();
		m.getResource();
	}
	
	@Test
	void ChargerTest() {
		Minion m = new Charger();
		m.getResource();
	}
	
	@Test
	void DeckTest() {
		Deck f = new Deck();
		Card c = new ArcherTowerCard();
		Deck d = new Deck(30);
		d.drawCard();
		d.add(c);
		System.out.println(d);
		d.isEmpty();
		d.shuffle();
		d.drawCard();
		d.getDeck();
		d.empty();
		d.getSize();
		d.getDeckAsList();
	}

}
