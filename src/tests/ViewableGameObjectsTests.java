package tests;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import game.TowerDefenseController;
import game.TowerDefenseView;
import viewable.Viewable;
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
	void BossTest() throws IOException {
		TowerDefenseController cont = new TowerDefenseController(new TowerDefenseView());
		Player p = new Player(cont);
		Minion m = new Boss(p);
		m.getResource();
	}
	
	@Test
	void CannonTowerTest() {
		Tower t = new CannonTower();
		t.getResource();
	}
	
	@Test
	void ChargerTest() throws IOException {
		TowerDefenseController cont = new TowerDefenseController(new TowerDefenseView());
		Player p = new Player(cont);
		Minion m = new Charger(p);
		m.getResource();
	}
	
	@Test
	void CurrencyTowerTest() {
		Tower t = new CurrencyTower();
		t.getResource();
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
	
	@Test
	void FreezeTowerTest() {
		Tower t = new FreezeTower();
		t.getResource();
	}
	
	@Test
	void GoblinTest() throws IOException {
		TowerDefenseController cont = new TowerDefenseController(new TowerDefenseView());
		Player p = new Player(cont);
		Minion m = new Goblin(p);
		m.getResource();
	}
	
	@Test
	void GoblinKnightTest() throws IOException {
		TowerDefenseController cont = new TowerDefenseController(new TowerDefenseView());
		Player p = new Player(cont);
		Minion m = new GoblinKnight(p);
		m.getResource();
	}
	
	@Test
	void HoundTest() throws IOException {
		TowerDefenseController cont = new TowerDefenseController(new TowerDefenseView());
		Player p = new Player(cont);
		Minion m = new Hound(p);
		m.getResource();
	}
	
	@Test
	void MageTowerTest() {
		Tower t = new MageTower();
		t.getResource();
	}
	
	@Test
	void MapTest() {
		Map m = new Map();
		m.setBoard(m.getBoard());
		m.flip();
		try {
			m.save("test.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			m.load("test.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	


}
