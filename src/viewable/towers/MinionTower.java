/** @author Chris Loper
 * 
 * Minion Tower Game Object
 * Contains default values for Minion Towers
 */
package viewable.towers;

import java.io.File;
import java.io.IOException;

public class MinionTower extends Tower {
	private static final int defaultAttack 			= 1;
	private static final int defaultRange 			= 0;
	private static final double defaultAttackSpeed	= 1;
	private static final String name				= "Minion Tower";
	
	public MinionTower() {
		super(defaultAttack, defaultRange, defaultAttackSpeed, name);
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
