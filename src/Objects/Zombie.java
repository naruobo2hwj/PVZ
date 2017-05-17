package Objects;
public class Zombie {
	public int row;
	public float x, y, speed;
	public int lifetime = 10;
	
	private int gridX = 250, gridY = 80;
	public int dx = 80, dy = 98;
	
	public boolean isDead = false;
	private boolean isEating = false;
	public boolean isBurnt = false;
	
	public Zombie(int row, float speed) {
		this.row = row;
		this.speed = speed;
		
		x = 1050;
		y = gridY + row*dy - 40;
	}
	
	public void move(){
		x -= speed;
		
		if(lifetime <= 0) isDead = true;
	}
	
	public boolean isEating(){
		return isEating;
	}
	
	public void startEating(){
		isEating = true;
	}
	
	public void resumeWalking(){
		isEating = false;
	}

}