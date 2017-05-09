public class Ball {
	private int gridX = 250, gridY = 80;
	public int dx = 80, dy = 100;
	
	public int row, y;
	public int size = 25;
	public float x, xSpeed;
	public boolean isDead = false;
	
	public Ball (int row, int x, float xSpeed){
		this.row = row;
		this.x = x;
		this.xSpeed = xSpeed;
		
		y = gridY + row*dy + 25;
	}
	
	public void move () {
		x += xSpeed;
		
		if(x > 1021) isDead = true;
	}
}