package Objects;

import PVZG.AnimatedImage;
import PVZG.PVZGame;
import processing.core.PImage;

public class CherryBomb extends Plant {
	
	PImage cherryBomb, powie;
	
	public int powieLifetime;
	
	public CherryBomb(PVZGame p, int row, int col) {
		super(p, row, col);
		lifeTime = 200;
		powieLifetime = 50;
		
		cherryBomb = p.loadImage("../resources/cherryBomb_HD.png");
		cherryBomb.resize(dx+40, dy-20);
		powie = p.loadImage("../resources/powie.png");
		powie.resize(dx+40, dy+40);
	}
	
	public void render(){
		if(isPowie()) p.image(powie, this.x-10, this.y-15);
		else p.image(cherryBomb, this.x-20, this.y+20);
	}
	
	public void tick(){
		System.out.println(lifeTime);
		if(isPowie()) powieLifetime--;
		else lifeTime--;
	}
	
	public boolean isDead(){
		return powieLifetime <= 0;
	}
	
	public boolean isPowie(){
		return lifeTime <= 0;
	}
}