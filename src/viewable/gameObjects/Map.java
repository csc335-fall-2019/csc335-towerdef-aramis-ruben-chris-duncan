/**
 * Purpose: This class is meant to hold the board map, with each viewable object,
 * allowing for serialization into a save file. 
 * @author Aramis Sennyey
 */

package viewable.gameObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import viewable.Viewable;

public class Map implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5882105144641498569L;
	
	private static final int NUM_ROWS = 13;
	private static final int NUM_COLS = 34;
	private static final int STACK_SIZE = 100;
	
	private Viewable[][][] board;
	
	public Map() {
		board = new Viewable[NUM_COLS][NUM_ROWS][STACK_SIZE];
	}
	
	public Viewable[][][] getBoard() {
		return board;
	}
	
	public void setBoard(Viewable[][][] v) {
		board = v;
	}
	
	public Map flip(){
		Viewable[][][] flipped = new Viewable[NUM_COLS][NUM_ROWS][STACK_SIZE];
		int last = 0;
		for(int i =0;i<board.length;i++) {
			last = NUM_ROWS-1;
			for(int j = 0;j<board[i].length;j++) {
				flipped[i][j] = board[NUM_COLS-1-i][last];
				last--;
			}
		}
		Map map = new Map();
		map.setBoard(flipped);
		return map;
	}
	
	public void save(String path) throws IOException {
		File file = new File(path);
		file.createNewFile();
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(this);
		System.out.println("Saved board...");
		out.close();
	}
	
	public void load(String path) throws Exception {
		File file = new File(path);
		if(!file.exists()) { // Would use UI to have user select file?
			throw new Exception("File not found.");
		}
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		this.board = ((Map)in.readObject()).getBoard();
		System.out.println("Read in board...");
		in.close();
	}
}
