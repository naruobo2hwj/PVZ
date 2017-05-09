public class Sunflower {
	public int row, col, x, y;
	public int lifetime = 600;
	public int sunTimer= 0;
	
	private int gridX = 250, gridY = 80;
	public int dx = 80, dy = 98;
	
	public Sunflower (int row, int col) {
		this.row = row;
		this.col = col;
		
		x = gridX + col*dx;
		y = gridY + row*dy;
	}
}