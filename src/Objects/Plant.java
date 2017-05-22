package Objects;
import PVZG.PVZGame;
import processing.core.PImage;

public class Plant {
	public int row, col, x, y;
	protected int gridX = 250, gridY = 80;
	protected int dx = 80, dy = 98;
	
	protected boolean isBeingEaten = false;
	protected int lifeTime;
	protected Zombie zombie;
	
	PVZGame p;
	
	public Plant(PVZGame p, int row, int col){
		this.p = p;
		
		this.row = row;
		this.col = col;
		
		x = gridX + col*dx;
		y = gridY + row*dy;
	}
	
	public void render(){}
	
	public void tick(){
		if(this.isBeingEaten()) this.decreaseLife();
	}
	
	public boolean zombieReached(Zombie z) {
		if(z.x <= this.x + dx && this.row == z.row) {
			isBeingEaten = true;
			this.zombie = z;
			return true;
		}
		return false;
	}

	public void decreaseLife() {
		lifeTime--;
		if(lifeTime == 0) zombie.resumeWalking();
	}
	
	public boolean isDead(){
		return lifeTime <= 0;
	}
	
	public boolean isBeingEaten(){
		return isBeingEaten;
	}
}
