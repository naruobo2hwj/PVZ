package Objects;

import PVZG.PVZGame;
import processing.core.PImage;

public class Sun {
	public int x;
	public float y, speed;
	private int age, lifeSpan;
	
	PVZGame p;
	PImage sun;
	
	public Sun (PVZGame p, int x, int y, float speed){
		this.p = p;
		
		this.x = x;
		this.y = y;
		this.speed = speed;
		lifeSpan = 800;
		this.age = 0;
		
		sun = p.loadImage("../resources/SunFA.png");
		sun.resize(50, 50);
	}
	
	public Sun (PVZGame p, int x, int y, float speed, int lifeSpan){
		this.p = p;
		
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.lifeSpan = lifeSpan;
		this.age = 0;
		
		sun = p.loadImage("../resources/SunFA.png");
		sun.resize(50, 50);
	}
	
	public void render(){
		p.image(sun, this.x, this.y);
	}
	
	public void tick(){
		this.age++;
		this.move();
	}
	
	public void move () {
		y += speed;
		if(y > 500) speed = 0;
	}
	
	public boolean isDead(){
		if(age >= lifeSpan) return true;
		return false;
	}
}
