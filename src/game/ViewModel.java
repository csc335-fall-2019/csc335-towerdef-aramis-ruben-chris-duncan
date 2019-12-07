package game;

import java.util.Observable;

public class ViewModel extends Observable {
	private int screenHeight;
	private int screenWidth;
	private int subtractedHeight;
	private int subtractedWidth;
	private int currentRow;
	private int currentCol;
	
	public ViewModel(int height, int width) {
		screenHeight = height;
		screenWidth = width;
		subtractedHeight = 0;
		subtractedWidth = 0;
	}

	public int getHeight() {
		return screenHeight;
	}
	
	public void setHeight(int h) {
		screenHeight = h;
	}
	
	public int getWidth() {
		return screenWidth;
	}
	
	public int getEffectiveWidth() {
		return screenWidth-subtractedWidth;
	}
	
	public void addSubWidth(int m) {
		subtractedWidth += m;
	}
	
	public void setWidth(int w) {
		screenWidth = w;
	}
	
	public int getSubtractedHeight() {
		return subtractedHeight;
	}
	
	public void addSubHeight(int m) {
		subtractedHeight += m;
	}
	
	public int getEffectiveBoardHeight() {
		return screenHeight-subtractedHeight;
	}
	
	public int getCurrentRow() {
		return currentRow;
	}
	
	public void setCurrentRow(int c) {
		currentRow = c;
	}
	
	public int getCurrentCol() {
		return currentCol;
	}
	
	public void setCurrentCol(int c) {
		currentCol = c;
	}
}
