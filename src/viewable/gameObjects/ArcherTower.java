package viewable.gameObjects;

import java.io.File;
import java.io.IOException;

public class ArcherTower extends Tower {

	private static final int defaultAttack 			= 1;
	private static final int defaultRange 			= 1;
	private static final double defaultAttackSpeed	= 1.0;
	
	public ArcherTower() {
		super(defaultAttack, defaultRange, defaultAttackSpeed);
	}
	
	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/tst.jpeg")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}
}
