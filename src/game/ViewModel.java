package game;

import java.util.Observable;

public class ViewModel extends Observable {
	private int screenHeight;
	private int screenWidth;
	private int menuHeight;
	private int currentRow;
	private int currentCol;
	private TowerDefenseView view;
	
	public ViewModel(int height, int width, TowerDefenseView view) {
		screenHeight = height;
		screenWidth = width;
		this.view = view;
		addObserver(view);
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
	
	public void setWidth(int w) {
		screenWidth = w;
	}
	
	public int getMenuHeight() {
		return menuHeight;
	}
	
	public void setMenuHeight(int m) {
		menuHeight = m;
	}
	
	public int getEffectiveBoardHeight() {
		return screenHeight-menuHeight;
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
