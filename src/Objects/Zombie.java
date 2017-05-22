package Objects;

import PVZG.AnimatedImage;
import PVZG.PVZDisplay;
import PVZG.PVZGame;
import processing.core.PImage;

public class Zombie {
	public int row;
	public float x, y, speed;
	public int lifetime = 10;
	
	private int gridX = 250, gridY = 80;
	public int dx = 80, dy = 98;
	
	public boolean isDead = false;
	private boolean isEating = false;
	public boolean isBurnt = false;
	
	PVZGame p;
	AnimatedImage walkingZombie, burntZombie;
	PImage eatingZombie;
	
	private int frameRate = 3;
	
	public Zombie(PVZGame p, int row, float speed) {
		this.p = p;
		
		this.row = row;
		this.speed = speed;
		
		x = 1050;
		y = gridY + row*dy - 40;
		
		walkingZombie = new AnimatedImage(p, "../resources/walkingConeZombiePics/", "frame_", "_delay-0.05s", 51, dx + 40, dy+60);
		burntZombie = new AnimatedImage(p, "../resources/incineratedZombiePics/", "frame_", "_delay-0.16s", 30, dx, dy+40);
		eatingZombie = p.loadImage("../resources/eatingZombie.png");
		eatingZombie.resize(dx, dy+40);
	}
	
	public void render(){
		if (this.isBurnt){
			burntZombie.display(this.x, this.y);
			if(burntZombie.finishedFirstLoop) this.isDead = true;
		}
		else if (this.isEating()) p.image(eatingZombie, this.x, this.y);
		else walkingZombie.display(this.x, this.y);
	}
	
	public void tick(){
		if(!this.isEating()) this.move();
		if(lifetime <= 0) isBurnt = true;
		if(PVZDisplay.timeInNanoseconds() % frameRate == 0){
			walkingZombie.tick();
			burntZombie.tick();
		}
	}
	
	public void move(){
		x -= speed;
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