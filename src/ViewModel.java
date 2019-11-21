
public class ViewModel {
	private int screenHeight;
	private int screenWidth;
	private int menuHeight;
	
	public ViewModel(int height, int width) {
		screenHeight = height;
		screenWidth = width;
	}
	
	public int getHeight() {
		return screenHeight;
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
}
