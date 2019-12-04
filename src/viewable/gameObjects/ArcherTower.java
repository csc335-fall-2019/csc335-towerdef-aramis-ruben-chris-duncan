/** @author Chris Loper
 * 
 * Archer Tower Game Object
 * Contains default values for Archer Towers
 */

package viewable.gameObjects;

import java.io.File;
import java.io.IOException;

public class ArcherTower extends Tower {

	private static final int defaultAttack 			= 1;
	private static final int defaultRange 			= 1;
	private static final double defaultAttackSpeed	= 1.0;
	private static final String name				= "Archer Tower";

	
	public ArcherTower() {
		super(defaultAttack, defaultRange, defaultAttackSpeed, name);
	}
	
	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/ArcherTower_Default.png")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}
}
