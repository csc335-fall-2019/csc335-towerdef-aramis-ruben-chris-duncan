/** @author Chris Loper
 * 
 * Currency Tower Game Object
 * Contains default values for Currency Towers
 */
package viewable.gameObjects;

import java.io.File;
import java.io.IOException;

public class CurrencyTower extends Tower {
	private static final int defaultAttack 			= 5;
	private static final int defaultRange 			= 0;
	private static final double defaultAttackSpeed	= 1;
	private static final String name				= "Currency Tower";
	
	public CurrencyTower() {
		super(defaultAttack, defaultRange, defaultAttackSpeed, name);
	}
	
	@Override
	public String getResource() {
		try {
			return (new File("./resources/images/CurrencyTower_Default.png")).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}
}
