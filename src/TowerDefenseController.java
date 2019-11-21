import towers.Tower;
import towers.TowerType;

public class TowerDefenseController {
	TowerDefenseBoard board;
	public TowerDefenseController() {
		board = new TowerDefenseBoard();
		test();
	}
	
	public void test() {
		board.addTower(0, 1, TowerType.BASICTOWER);
	}

	public Tower[][] getBoard() {
		// TODO Auto-generated method stub
		return board.getBoard();
	}
}
