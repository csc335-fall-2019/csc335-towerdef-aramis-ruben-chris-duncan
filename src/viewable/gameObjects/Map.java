/**
 * Purpose: This class is meant to hold the board map, with each viewable object,
 * allowing for serialization into a save file. 
 * @author Aramis Sennyey
 */

package viewable.gameObjects;

import java.io.Serializable;

import viewable.Viewable;

public class Map implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5882105144641498569L;
	
	private static final int NUM_ROWS = 11;
	private static final int NUM_COLS = 18;
	private static final int STACK_SIZE = 100;
	
	private Viewable[][][] board;
	
	public Map() {
		board = new Viewable[NUM_COLS][NUM_ROWS][STACK_SIZE];
	}
	
	public Viewable[][][] getBoard() {
		return board;
	}
}
