/** @author Chris Loper
 * 
 * Cannon Tower Game Object
 * Contains default values for Cannon Towers
 */
package viewable.gameObjects;

import java.io.File;
import java.io.IOException;

public class CannonTower extends Tower {
	private static final int defaultAttack 			= 3;
	private static final int defaultRange 			= 1;
	private static final double defaultAttackSpeed	= 0.5;
	private static final String name				= "Cannon Tower";
	
	public CannonTower() {
		super(defaultAttack, defaultRange, defaultAttackSpeed, name);
	}
	
	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/CannonTower_Default.png")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}
}
