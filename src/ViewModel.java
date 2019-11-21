
public class ViewModel {
	private int screenHeight;
	private int screenWidth;
	
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
}
