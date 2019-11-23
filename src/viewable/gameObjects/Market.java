package viewable.gameObjects;

import viewable.cards.abilityCards.DrawCard;
import viewable.cards.abilityCards.SummonMinionCard;
import viewable.cards.towers.CannonTowerCard;
import viewable.cards.towers.CurrencyTowerCard;
import viewable.cards.towers.FreezeTowerCard;
import viewable.cards.towers.MageTowerCard;
import viewable.cards.towers.MinionTowerCard;

public class Market {
	
	private Deck market;
	private Deck forSale;
	
	public Market() {
		fillMarket();
		market.shuffle();
		populateForSale();
	}

	private void fillMarket() {
		market.add(new DrawCard());
		market.add(new DrawCard());
		market.add(new SummonMinionCard());
		market.add(new SummonMinionCard());
		market.add(new SummonMinionCard());
		market.add(new SummonMinionCard());
		market.add(new CannonTowerCard());
		market.add(new CurrencyTowerCard());
		market.add(new FreezeTowerCard());
		market.add(new MageTowerCard());
		market.add(new MinionTowerCard());
		market.add(new MinionTowerCard());
	}
	
	public void populateForSale() {
		int x = 6 - forSale.getSize();
		for (int i = 0; i < x; i++) {
			forSale.add(market.drawCard());
		}
	}
	
}
