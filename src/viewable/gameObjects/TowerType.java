package viewable.gameObjects;

public enum TowerType {
	BASICTOWER(ArcherTower.class);
	
	private Class<? extends Tower> c;
	private TowerType(Class<? extends Tower> c) {
		this.c = c;
	}
	
	public Class<? extends Tower> getTower() {
		return c;
	}
}
