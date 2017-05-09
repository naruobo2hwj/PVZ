public class CherryBomb {
	public int row, col, x, y;
	public int lifetime = 100;
	
	private int gridX = 250, gridY = 80;
	public int dx = 80, dy = 98;
	
	public CherryBomb(int row, int col) {
		this.row = row;
		this.col = col;
		
		x = gridX + col*dx;
		y = gridY + row*dy;
	}
	
	
}