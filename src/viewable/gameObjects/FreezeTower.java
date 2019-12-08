/** @author Chris Loper
 * 
 * Freeze Tower Game Object
 * Contains default values for Freeze Towers
 */
package viewable.gameObjects;

import java.io.File;
import java.io.IOException;

public class FreezeTower extends Tower {
	private static final int defaultAttack 			= 0;
	private static final int defaultRange 			= 1;
	private static final double defaultAttackSpeed	= 0.5;
	private static final String name				= "Freeze Tower";
	
	public FreezeTower() {
		super(defaultAttack, defaultRange, defaultAttackSpeed, name);
	}
	
	@Override
	public String getResource() {
		if (this.getUpgraded()) {
			try {
				return (new File("./resources/images/FreezeTower_Upgrade.png")).getCanonicalPath();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return "";
			}
		} else {
			try {
				return (new File("./resources/images/FreezeTower_Default.png")).getCanonicalPath();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return "";
			}
		}
	}
}
