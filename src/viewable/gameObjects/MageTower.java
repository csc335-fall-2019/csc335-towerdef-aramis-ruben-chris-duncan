/** @author Chris Loper
 * 
 * Mage Tower Game Object
 * Contains default values for Mage Towers
 */
package viewable.gameObjects;

import java.io.File;
import java.io.IOException;

public class MageTower extends Tower {
	private static final int defaultAttack 			= 1;
	private static final int defaultRange 			= 2;
	private static final double defaultAttackSpeed	= 0.75;
	private static final String name				= "Mage Tower";
	
	public MageTower() {
		super(defaultAttack, defaultRange, defaultAttackSpeed, name);
	}
	
	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/MageTower_Default.png")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}
}
