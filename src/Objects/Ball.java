package Objects;

import PVZG.PVZGame;

public class Ball {
	private int gridX = 250, gridY = 80;
	public int dx = 80, dy = 100;
	
	public int row, y;
	public int size = 25;
	public float x, xSpeed;
	public boolean isDead = false;
	
	PVZGame p;
	
	public Ball (PVZGame p, int row, int x, float xSpeed){
		this.p = p;
		
		this.row = row;
		this.x = x;
		this.xSpeed = xSpeed;
		
		y = gridY + row*dy + 25;
	}
	
	public void render(int green) {
		p.fill(green);
		p.ellipse(this.x, this.y, this.size, this.size);
	}
	
	public void tick() {
		move();
	}
	
	public void move () {
		x += xSpeed;
		
		if(x > 1021) isDead = true;
	}
}