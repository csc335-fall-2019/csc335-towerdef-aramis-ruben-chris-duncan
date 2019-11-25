package game;

public class ViewModel {
	private int screenHeight;
	private int screenWidth;
	private int menuHeight;
	private int currentRow;
	private int currentCol;
	private TowerDefenseView view;
	private int topHeight;
	private int bottomHeight;
	private int leftWidth;
	
	public ViewModel(int height, int width, TowerDefenseView view) {
		screenHeight = height;
		screenWidth = width;
		this.view = view;
	}
	
	public int getHeight() {
		return screenHeight;
	}
	
	public void setHeight(int h) {
		screenHeight = h;
	}
	
	public void setTopHeight(int t) {
		topHeight = t;
	}
	
	public void setBottomHeight(int b) {
		bottomHeight = b;
	}
	
	public void setLeftWidth(int l) {
		leftWidth = l;
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
		return screenHeight-menuHeight-topHeight-bottomHeight;
	}
	
	public int getEffectiveBoardWidth() {
		return screenWidth-leftWidth;
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
