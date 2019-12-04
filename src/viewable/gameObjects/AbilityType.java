package viewable.gameObjects;

public enum AbilityType {
	DamageCard(DamageCard.class);
	DrawCard(DrawCard.class);
	HealCard(HealCard.class);
	IncreaseRewardCard(IncreaseRewardCard.class);
	
	
	
	private Class<? extends AbilityCard> a;
	private AbilityType(Class<? extends AbilityCard> a) {
		this.a = a;
	}
	
	public Class<? extends AbilityCard> getAbility() {
		return a;
	}
}
